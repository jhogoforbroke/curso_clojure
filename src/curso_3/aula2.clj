(ns curso_3.aula2
  (:use [clojure pprint])
  (:require [hospital.model :as h.model])
  (:require [hospital.logic :as h.logic]))

; ---------------------
; trabalhando com filas
; ---------------------

; sistema de filas de espera de um hospital
(defn simula-um-dia
  []
  (def hospital (h.model/novo-hospital)) ; instancia novas filas do hospital

  ; joao e outros chegam na fila de espera (adiciona)
  (def hospital (h.logic/chega-em hospital :espera "Joao"))
  (def hospital (h.logic/chega-em hospital :espera "Maria"))
  (def hospital (h.logic/chega-em hospital :espera "Algusto"))
  (def hospital (h.logic/chega-em hospital :espera "Samanta"))
  (def hospital (h.logic/chega-em hospital :espera "Alice"))

  ; carlos chega a fila do clinico (adiciona)
  (def hospital (h.logic/chega-em hospital :clinico-geral "Carlos"))

  ; Antonio chega a fila do oftalmo (adiciona)
  (def hospital (h.logic/chega-em hospital :oftalmologista "Antonio"))

  ; hospital tem
  ;{:espera         <- (" Joao" "Maria" "Algusto" "Samanta" "Alice") - <,
  ; :clinico-geral  <- (" Carlos") -<,
  ; :oftalmologista <- (" Antonio") -<,
  ; :ortopedista    <- () -<}
  (pprint hospital)

  ; chega-em tem limite de 5 pessoas na fila de adicionar mais um erro de fila cheia
  ;(def hospital (h.logic/chega-em hospital :espera "Helen"))

  ; retira primeiro da fila e devolve fila sem o primeiro e redefine o simbulo hospital
  (def hospital (h.logic/atende hospital :clinico-geral))
  (pprint hospital)

  ; retira primeiro da fila e devolve fila sem o primeiro e redefine o simbulo hospital
  (def hospital (h.logic/atende hospital :oftalmologista))
  (pprint hospital))

(simula-um-dia)


; ---------
; concorrência e paralelismo
; ---------
(defn funcao-em-paralelo
  []
  (.start (Thread. (fn [] (println "hello_2"))))
  (.start (Thread. (fn [] (println "hello_3"))))
  (.start (Thread. (fn [] (println "hello_4"))))
  (.start (Thread. (fn [] (println "hello_5"))))
  (.start (Thread. (fn [] (println "hello_6"))))
  (.start (Thread. (fn [] (println "hello_7"))))
  (.start (Thread. (fn [] (println "hello_8"))))
  (.start (Thread. (fn [] (println "hello_9"))))
  (.start (Thread. (fn [] (println "hello_10")))))

(funcao-em-paralelo)

(defn chega-em-malvado
  [hospital departamento pessoa]
  (Thread/sleep (*(rand-int 5) 1000))
  (.start (Thread. (fn [] (def hospital (h.logic/chega-em hospital :espera pessoa))))))

(defn imrpime-hospital-apos-10-segundos
  []
  (.start (Thread.
            (fn [] (
                    (Thread/sleep 10000)
                    (pprint hospital))))))

(defn chega-em-paralelo
  []
  (def hospital (h.model/novo-hospital))
  (chega-em-malvado hospital :espera "primeiro")
  (chega-em-malvado hospital :espera "segundo")
  (chega-em-malvado hospital :espera "terceiro")
  (chega-em-malvado hospital :espera "quarto")
  (chega-em-malvado hospital :espera "quinto")
  (chega-em-malvado hospital :espera "sexto")

  (pprint hospital)

  (imrpime-hospital-apos-10-segundos))

(pprint (chega-em-paralelo))
