(ns curso_3.aula5
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic])
  (:require [hospital.model :as h.model]))

; --------------
; atomicidade 2
; --------------

(defn chega-em!
  [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa))

(defn transfere!
  [hospital de para]
  (swap! hospital h.logic/transfere de para))

(defn simula-um-dia []
  (let [hospital (atom (h.model/novo-hospital))]

    (chega-em! hospital "Terry Bogard")
    (chega-em! hospital "Leona")
    (chega-em! hospital "Robert Garcia")
    (chega-em! hospital "Goro Daimon")

    (transfere! hospital :espera :clinico-geral)
    (transfere! hospital :espera :clinico-geral)
    (transfere! hospital :espera :clinico-geral)
    (transfere! hospital :espera :clinico-geral)

    (transfere! hospital :clinico-geral :oftalmologista)
    (transfere! hospital :clinico-geral :oftalmologista)
    (transfere! hospital :clinico-geral :ortopedista)
    (transfere! hospital :clinico-geral :ortopedista)

    (pprint hospital)))

(simula-um-dia)
