(ns hospital.logic)

(defn cheio?
  [hospital departamento]
  (-> hospital
      (get ,,, departamento)
      (count ,,,)
      (< ,,, 5)))

(defn chega-em
  [hospital departamento pessoa]
  (if (cheio? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "fila cheia" { :tentando-adicionar pessoa }))))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn proximo
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  [hospital de para]
  (let [pessoa (proximo hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))

(defn atende-completo
  [hospital departamento]
  {:paciente (update hospital departamento peek)
   :hospital (update hospital departamento pop)})

(defn atende-peek-pop-com-juxt
  [hospital departamento]
  (let [fila (get hospital departamento)
        peek-pop (juxt peek pop)
        [proximo fila-atualizada] (peek-pop fila)
        hospital-atualizado (update hospital assoc departamento fila-atualizada)]
    {:pessoa proximo
     :hospital hospital-atualizado}))