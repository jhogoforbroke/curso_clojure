(ns hospital.model)

(def fila-vazia clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital
  []
  {:espera         fila-vazia
   :clinico-geral  fila-vazia
   :oftalmologista fila-vazia
   :ortopedista    fila-vazia})