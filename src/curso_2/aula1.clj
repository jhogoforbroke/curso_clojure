(ns curso_2.aula1)

; -----------------------
; Trabalhando com arrays
; -----------------------

; definindo um array de nomes
(def nomes ["heisenberg" "jimmy" "jessy" "mike"])
(map println nomes)

; pega primeiro elemento do array
(first nomes)

; pega array com ponteiro apontando para o proximo
(rest nomes)
(next nomes)

; rest quando array esta vazio
; (passou pelo array inteiro )
; devolve () "seq"
(rest [])

;next quando array esta vazio
; (passou pelo array inteiro)
; devolve nil
(next [])

; é possivel transformar e fazer casts para seq
(seq [1 2 3 4 5])

; -----------------------
; IMPLEMENTANDO UM MAP
; -----------------------
(defn meu-map
  "implementacao minha tosca de map"
  [function sequence] ; recebe uma função para aplicar e uma sequencia
  (let [next-element (first sequence)] ; instancia localmente o proximo elelemento
    (if (not (nil? next-element)) ; verifica se o proximo elemento NÃO é nulo
      (do
        (function next-element)  ; chama a função para o "current" elemento
        (meu-map function (next sequence))))))   ; chama map recursivamente

(meu-map println ["heisenberg" "jimmy" "jessy" "mike"])

; StackOverflowError!!! a função
; recursiva ira uma hora estourar a pila
; pois esta empilhando os escopos das funções
; (meu-map println (range 10000))

; TAIL RECURSION
; Maneira correta de fazer recursão
(defn meu-map
  "implementacao minha tosca de map"
  [function sequence]
  (let [next-element (first sequence)]
    (if (not (nil? next-element))
      (do
        (function next-element)
        (recur function (next sequence)))))) ; chama a recursao com a palavra chave recur

(meu-map println (range 10000))
; -----------------------
