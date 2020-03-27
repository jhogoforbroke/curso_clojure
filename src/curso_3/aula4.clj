(ns curso_3.aula4
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic])
  (:require [hospital.model :as h.model]))


; --------------
; paralelismo 2
; --------------


(defn pause-e-chega-em
  "espera 2 seg printa e chama chega-em"
  [hospital departamento pessoa]
  (Thread/sleep 2000)
  (println "Tentando Adicionar" pessoa)
  (h.logic/chega-em hospital departamento pessoa))

(defn chega-em-malvado!
  [hospital pessoa]
  (swap! hospital pause-e-chega-em :espera pessoa))

; ----------
; Swap
; ----------
; todas as threads sao disparadas ao mesmo tempo
; porem ao executar pausa-e-chega-em o metodo leva 2 seg
; para ser executado
; o que força nosso swap ao verificar novamente se o valor
; do atomo que enviou em chega-em-malvado foi alterado,
; uma vez que esse valor tenha sido alterado pela
; chamada em paralelo anterior o swap faz um retry e verifica
; a atomicidade novamente, assim sussesivamenta ate que todos
; os valores consigam ser inserido
(defn chega-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "primeiro"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "segundo"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "terceiro"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "quarto"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "quinto"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "sexto"))))
    (.start (Thread. (fn [] (Thread/sleep 14000)
                       (println "resultado final:")
                       (pprint @hospital))))))

;----------------
; REFACTOR - map
;----------------
; MAP TEM EXECUSAO LAXY POR TANTO NAO HAVERA ALERACAO DENTRO DA THREAD
(defn chega-em-paralelo-refator-map
  []
  (let [hospital (atom (h.model/novo-hospital))
        ; transforma em um simbolo local como array de pessoas
        pessoas ["primeiro" "segundo" "terceiro" "quarto" "quinto" "sexto"]]
    ; chama a função como um map em cima do array de pessoas
    (map #(.start (Thread. (fn [] (chega-em-malvado! hospital %)))) pessoas)
    ; apos 14 seg imprime o resultado final
    (.start (Thread. (fn [] (Thread/sleep 14000)
                       (println "resultado final:")
                       (pprint @hospital))))))
; o resultado é um mapa vazio pois o map executa de forma lazy
; e nao tem alteração dentro da thread


;----------------
; REFACTOR - mapv
;----------------
; com mapv (comportamento eager) as alterações sao executadas dentro das threads
(defn chega-em-paralelo-refator-mapv
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["primeiro" "segundo" "terceiro" "quarto" "quinto" "sexto"]]
    (mapv #(.start (Thread. (fn [] (chega-em-malvado! hospital %)))) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 14000)
                       (println "resultado final:")
                       (pprint @hospital))))))


; refatora para um simbulo local a funcao que starta a thread
(defn chega-em-paralelo-refator-mapv-fn-starta-thread
  []
  (let [hospital (atom (h.model/novo-hospital))
        ; array de pessoas
        pessoas ["primeiro" "segundo" "terceiro" "quarto" "quinto" "sexto"]
        ; extração da funcao que starta a thread em um simbolo
        starta-thread #(.start (Thread. (fn [] (chega-em-malvado! hospital %))))]
    ; executa funcao starta thread para cada pessoa com mapv
    (mapv starta-thread pessoas)
    ; apos 14 seg imprime resultado final
    (.start (Thread. (fn [] (Thread/sleep 14000)
                       (println "resultado final:")
                       (pprint @hospital))))))


;----------------
; REFACTOR - mapv - overload
;----------------
; extrai para um funcao externa a funcao que starta a thread
(defn starta-thread-overload
  ;overload 1
  ; recebe hospital e devolve uma implementacao
  ; que so precisa receber pessoa e chama o overload 2
  ; com hospital recebido e pessoa
  ([hospital]
   (fn [pessoa] (starta-thread-overload hospital pessoa)))

  ; overload 2
  ; recebe hospital e pessoa e starta a thread como chega em
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-em-malvado! hospital pessoa))))))

(defn chega-em-paralelo-refator-mapv-extrai-fn-starta-thread-overloads
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["primeiro" "segundo" "terceiro" "quarto" "quinto" "sexto"]
        ; declara ja um simbulo interno que representa a chamada da funcao
        ; start thread ja paddando o hospital
        start-thread-com-overload-passando-hospital (starta-thread-overload hospital)]

    ; mapv so recebe 1 parametro
    ; chamamos entao o primeiro overload de starta-thread
    ; que recebe hospital e devolve uma funcao que so precisa receber pessoa
    (mapv start-thread-com-overload-passando-hospital pessoas)

    (.start (Thread. (fn [] (Thread/sleep 14000)
                       (println "resultado final:")
                       (pprint @hospital))))))


;----------------
; REFACTOR - mapv - PARTIAL
;----------------
; extrai para um funcao externa a funcao que starta a thread
(defn starta-thread-partial
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-em-malvado! hospital pessoa)))))

(defn chega-em-paralelo-refator-mapv-extrai-fn-starta-thread-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["primeiro" "segundo" "terceiro" "quarto" "quinto" "sexto"]
        ; PARTIAL
        ; patial funciona da mesma forma
        ; vai executar a funcao que devolve
        ; a mesma funcao com o parametro passado
        ; anteriormento mais o proximo
        ; paramtro passado, como segundo parametro
        start-thread-com-partial (partial starta-thread-partial hospital)]

    ; mapv so recebe 1 parametro
    ; chama funcao que recebe hospitaql e é declarada como partial
    ; essa funcao sera executada e retornara ela mesma
    ; recebendo como parametro o proximo paramtro, no caso pessoa
    ; sendo assim starta-thread-partial recece [hospital pessoa]
    (mapv start-thread-com-partial pessoas)

    (.start (Thread. (fn [] (Thread/sleep 14001)
                       (println "resultado final:")
                       (pprint @hospital))))))


;----------------
; REFACTOR - doseq
;----------------
; doseq executa como um for of
(defn starta-thread-doseq
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-em-malvado! hospital pessoa)))))

(defn chega-em-paralelo-refator-mapv-extrai-fn-starta-thread-doseq
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["primeiro" "segundo" "terceiro" "quarto" "quinto" "sexto"]]

    ; para cada pessoa executa o starta-thread-doseq com hospital e pssoa
    (doseq [pessoa pessoas]
      (starta-thread-doseq hospital pessoa))

    (.start (Thread. (fn [] (Thread/sleep 14001)
                       (println "resultado final:")
                       (pprint @hospital))))))



;----------------
; REFACTOR - dotimes
;----------------
; executa para um numero determinado de vezes
(defn starta-thread-doseq
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-em-malvado! hospital pessoa)))))

(defn chega-em-paralelo-refator-mapv-extrai-fn-starta-thread-dotimes
  []
  (let [hospital (atom (h.model/novo-hospital))]

    (dotimes [pessoa 6]
      (starta-thread-doseq hospital pessoa))

    (.start (Thread. (fn [] (Thread/sleep 14001)
                       (println "resultado final:")
                       (pprint @hospital))))))
