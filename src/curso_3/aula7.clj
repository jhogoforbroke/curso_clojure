(ns curso_3.aula7
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))

; -------------
; FUTURES
; -------------

; função pura que verifica
; se tamanho da fila é maior que 4
(defn cabe-na-fila?
  [fila]
  (-> fila
      count
      (< 4)))

; função pura que adiciona na fila caso
; tamanho maximo nao tenha sido atingido
; se tamanho maximo atingido dispara exception
(defn chega-em
  [fila pessoa]
  (if (cabe-na-fila? fila)
    (conj fila pessoa)
    (throw (ex-info "fila cheia" { :pessoa pessoa }))))

; função que altera fila de forma atomica (alter)
; adiconando pessoa que chega
(defn chega-em!
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))

; simula um dia para testar se regra de fila cheia esta funcionando ok
(defn simula-um-dia []
  (let [hospital {:espera         (ref h.model/fila-vazia)
                  :clinico-geral  (ref h.model/fila-vazia)
                  :oftalmologista (ref h.model/fila-vazia)}]

    ; somente permite adiciona 4 pessoas
    ; se adicionar 5 dispara erro
    (dosync
      (chega-em! hospital "Leona")
      (chega-em! hospital "Rauf")
      (chega-em! hospital "Clark")
      ;(chega-em! hospital "Whip")
      (chega-em! hospital "Heidern"))

    (pprint hospital)))

(simula-um-dia)

; ------------
; FUTURE
; ------------
; chega em de forma async
; cria uma thread onde o resultado sera retornado no futuro
(defn async-chega-em!
  [hospital pessoa]
  ; retultado sera retornado apos o sleep de 0 a 5 segundos
  (future (Thread/sleep (rand 5000))
          (dosync
            (println "tentando adicionar na fila" pessoa)
            (chega-em! hospital pessoa))))


; simula um dia de forma paralela
(defn async-simula-um-dia []
  (let [hospital {:espera         (ref h.model/fila-vazia)
                  :clinico-geral  (ref h.model/fila-vazia)
                  :oftalmologista (ref h.model/fila-vazia)}]

    ; repete 10x o chega in async para as pessoas de 0 a 9
    (dotimes [pessoa 10]
      (async-chega-em! hospital pessoa))

    ; como os futures ainda nao foram resolvidos (ready)
    ; o hospital printado sera vazio
    (pprint hospital)

    ; future que ira printar o hospital
    ; apos o termino dos sleep de 6 segundos
    ; nesse momento os futures de aync-chega-em
    ; ja terao sido resolvidos (ready)
    ; printando assim o resultado do hospital com
    ; 4 pessoas possiveis inseridas
    (future
        (Thread/sleep 6000)
        (pprint hospital))
      ; porem os erros nao serão mostrados,
      ; pois a future funciona como um promise
      ; encapsulando dentro da future
      ; o retorno, o sucesso e a falha
    ))

(async-simula-um-dia)
