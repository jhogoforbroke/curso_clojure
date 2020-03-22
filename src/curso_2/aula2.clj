(ns curso_2.aula2)

; --------------------
; loops e recursao
; --------------------

(def nomes ["heisenberg" "jimmy" "jessy" "mike", "gustavo", "hector"])

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

; IMPLEMENTANDO UM REDUCE
(defn conta-elementos
  "conta total de elemento de uma sequencia"
  [total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(conta-elementos 0 nomes)
(conta-elementos 0 [])

; OVERLOAD de função
(defn conta-elementos-overload
  "conta total de elemento de uma sequencia"

  ; quando recebe UM parametro
  ; overload 1 chama funcao com inicializador 0
  ([elementos]
   (conta-elementos-overload 0 elementos))

  ; quando recebe DOIS parametros
  ; overload 2 chama funcao com
  ; total ate agora da recursao anterios
  ; e os elementos que ainda faltam
  ([total-ate-agora elementos]
   (if (seq elementos)
     (recur (inc total-ate-agora) (next elementos))
     total-ate-agora)))

(conta-elementos-overload nomes)
(conta-elementos-overload [])

; LOOP
(defn conta-elementos-loop
  "conta total de elemento de uma sequencia"
  [elementos]

  ; define elementos do loop que podem alterar de valor a cada "loopada"
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos-restantes) ; enquanto tiver valor na seq
      ; incrementa total e troca pra proximo elemento dentro do loop
      (recur (inc total-ate-agora) (next elementos-restantes))

      total-ate-agora))) ; fora do loop retorna o total

(conta-elementos-loop nomes)
(conta-elementos-loop [])