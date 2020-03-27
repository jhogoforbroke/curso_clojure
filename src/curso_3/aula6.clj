(ns curso_3.aula6
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))

; -----------------------
; dosync - ref-set e alter
; -----------------------

; função pura - adiciona pessoa na fila
(defn chega-em
  [fila pessoa]
  (conj fila pessoa))

; ----------
; REF-SET
; ----------
; altera valor de forma atomica
; com retry dentro de uma transação (dosync)
(defn chega-em-ref-set!
  [hospital pessoa]
  (let [fila (get hospital :espera)] ; pega a fila de espera do hospital
    ; seta a referencia do
    ; simbulo fila com o resultado
    ; de chega-em passando a derref (@)
    ; do simbulo atomico (ref) fila e a pessoa
    (ref-set fila (chega-em @fila pessoa))))

; ----------
; Alter
; ----------
; com retry dentro de uma transação (dosync)
; funciona da mesma maneira que o ref-set,
; porem tem sintaxe mais parecida com swap!
; recebendo na ordem:
; - o valor aromico (ref),
; - a função
; - os outros paramtros
(defn chega-em-alter!
  [hospital pessoa]
  (let [fila (get hospital :espera)] ; pega a fila de espera do hospital
    ; altera de forma atomica atraves de
    ; retry o valor atomico de fila (ref)
    ; recebendo como parametro a função pura chega-em e a pessoa
    (alter fila chega-em pessoa)))

(defn simula-um-dia []
  ; define hospital com valores atomicos nas filas como ref
  (let [hospital {:espera         (ref h.model/fila-vazia)
                  :clinico-geral  (ref h.model/fila-vazia)
                  :oftalmologista (ref h.model/fila-vazia)
                  :ortopedista    (ref h.model/fila-vazia)}]

    ; ---- bloco transação ---
    ; inicia a transação com dosync
    (dosync
      ; adiciona chegada do paciente com ref-set
      (chega-em-ref-set! hospital "Heisenberg")

      ; adiciona chegada do paciente com alter
      (chega-em-alter! hospital "Hector Salamenca"))
    ; ---- bloco transação ---

    (pprint hospital)))

(simula-um-dia)
