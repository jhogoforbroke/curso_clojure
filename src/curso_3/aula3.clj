(ns curso_3.aula3
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic])
  (:require [hospital.model :as h.model]))

; ------------
; atomicidade
; ------------

; ----------------
; Simbolos Globais
; ----------------

; global sempre que redefinido o simbolo é sobreescrito
(def nome "GLOBAL JOAO")
(def nome "GLOBAL MARIA")

(pprint nome)

; ----------------
; Simbolos locais
; ----------------

; com let o feito um shadow do escopo onde cada let tem seu proprio escopo
(let [nome "ESCOPO SUPERIOR - Joao"]
  (pprint nome)

  (let [nome "ESCOPO INTERNO - Maria"]
    ; printa simbolo do escopo interno "MARIA"
    (pprint nome))

  ; printa simbolo do escopo superior "JOAO"
  (pprint nome))


; ----------------
; ATOMOS
; ----------------
(defn alterando-atomo
  []
  (let [hospital-atomico (atom { :espera h.model/fila-vazia })]
    (print hospital-atomico)
    (pprint hospital-atomico)

    ; deref pega uma copia do que tem dentro do atomo
    (pprint (deref hospital-atomico))
    (pprint @hospital-atomico) ;pode ser usado com atalho @

    ; somente altera a copia do que foi EXTRAIDO DO ATOMO
    (assoc @hospital-atomico :clinico-geral h.model/fila-vazia)
    ; atomo continua igual
    (pprint @hospital-atomico)

    ; como alterar um atomo
    (swap! hospital-atomico assoc :clinico-geral h.model/fila-vazia)
    (pprint @hospital-atomico)

    ; update adicionando na fila
    ; maneira redefinindo a copia do que foi extraido do atomo
    (update @hospital-atomico :clinico-geral conj "primeiro")
    (pprint @hospital-atomico)

    ; com swap
    (swap! hospital-atomico update :clinico-geral conj "primeiro")
    (pprint @hospital-atomico)

    ))

(alterando-atomo)



; ------------------------------------------------
; Simulador de atendimento do hosportal com atomos
; ------------------------------------------------

(defn pause-e-chega-em
  [hospital departamento pessoa]
  (Thread/sleep 2000)
  (println "Tentando Adicionar" pessoa)
  (h.logic/chega-em hospital departamento pessoa))

(defn chega-em-malvado!
  [hospital pessoa]
  (swap! hospital pause-e-chega-em :espera pessoa))

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
                       (pprint @hospital)
                       )))))

(chega-em-paralelo)
