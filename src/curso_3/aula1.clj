(ns curso_3.aula1)

; ----------
; Coleções
; ----------

; ARRAY
(defn test-array
  []
  (println "vetor")
  (let [arr [1 2 3 4 5]] ; define vetor com elemento de 1 a 5
    (println arr)
    (println (conj arr 6)) ; conj insere no FINAL do vetor
    (println (conj arr 6)) ; imutavel, portanto adiciona 6 ao vetor original [1,2,3,4,5,6]
    (println (pop arr)))) ; pop retira o ULTIMO elemento do vetor [1,2,3,4]

(test-array)


; LISTA LIGADA
(defn test-lista
  []
  (println "lista ligada")
  (let [list '(1 2 3 4 5)] ; define lista com elemento de 1 a 5
    (println list)
    (println (conj list 0)) ; conj insere no INICIO da lista ligada
    (println (conj list 0)) ; imutavel, portanto adiciona 0 a lista original (0,1,2,3,4,5)
    (println (pop list)))) ; pop retira o PRIMEIRO elemento da lista (2,3,4,5)

(test-lista)


; SET
(defn test-conjunto
  []
  (println "conjunto")
  (let [set #{1 2 3 4 5}] ; define conjunto com elemento de 1 a 5
    (println set)
    (println (conj set 9)) ; conj insere no conjuneto (nao possui ordem)
    (println (conj set 9)) ; não adiciona, conjunto só possiu elementos unicos
    ; (println (pop set))))  pop retira o PRIMEIRO elemento da lista (2,3,4,5)
    ))

(test-conjunto)


; QUEUE
(defn test-fila
  []
  (println "fila")
  ; define queue com elemento de 1 a 5
  (let [queue (conj clojure.lang.PersistentQueue/EMPTY 1 2 3 4 5)]
    (println (seq queue))
    (println (seq (conj queue 6))) ; conj insere no FINAL da fila
    (println (seq (conj queue 6))) ; imutavel, portanto adiciona 6 a fila original (0,1,2,3,4,5)
    (println (seq (pop queue))) ; pop retorna os elementos retirado o primeiro (2,3,4,5)
    (println (peek queue)))) ; peek pega o primeiro elemento da fila

(test-fila)