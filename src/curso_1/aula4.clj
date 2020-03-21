(ns curso_1.aula4)

; ----------------------
; Trabalhando com Arrays
; ----------------------

; define array de numeros, chamado precos
(def precos [30 700 1000])


; -----------
; GET
; -----------
; pega primeiro elemento do array com metodo get
(get precos 0)

; pega segundo elemento do array com metodo get
(get precos 1)

; get trata quando index fora do array e retorna nulo
(def index-que-nao-existe 500000000)
(get precos index-que-nao-existe)

; pode ser definido um valor default caso nao tiver o index
(get precos index-que-nao-existe 0)
; -----------


; -----------
; CONJ
; -----------
; conj adiciona no final do array o elemento
; MAS COMO O ARRAY ORIGINAL É IMUTAVEL, NAO ALTERA O ARRAY ORIGINAL
(conj precos 300)
;  o array original nao é alterado, pois é imutável
(println precos)
; -----------


; inc incrementa 1
(inc 5)
(println (+ 5 1))

; dec decrementa 1
(dec 5)
(println (- 5 1))


; -----------
; UPDATE
; -----------
; update aplica a função (ultimo param)
; no array (primeiro param), indice x (segundo param)
(update precos 0 inc)
(update precos 1 inc)
(update precos 1 dec)

(defn soma-3
  "retona valor +3"
  [valor]
  (+ valor 3))

(update precos 2 soma-3)
; -----------


(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  "retona o valor com desconto de 10% do valor bruto"
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (println "Calcula desconto de 10% de" valor-bruto "=" desconto)
      (- valor-bruto desconto))
    valor-bruto))


; -----------
; MAP
; -----------
; primeiro arg funcao que sera executada para cada item
; segundo arg array que sera executado a funcao para cada item
(map valor-descontado precos)

; range (gera array de 10 elelemtos: 0 a 9)
(range 10)
; -----------

; -----------
; FILTER
; -----------
; filter aplica filtro primeiro param no array segundo param
(filter even? (range 10))
(filter aplica-desconto? precos)
; -----------


; -----------
; REDUCE
; -----------
; aplica a funcao para todos os elemento
; começando aplicar primeiro com segundo,
; e o resultado com o terceiro,
; e o resultado com o quarto...
(reduce + precos) ; 1730
(reduce + (range 10)) ; 45
; recure pode comecar com valor inicial
(reduce + 55 (range 10)) ; 100
; -----------
