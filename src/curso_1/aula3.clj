(ns curso_1.aula3)

; ---------------------
; Uso Basico De Funções
; ---------------------

; Define uma function que retona um bool
(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

; function que recebe uma valor e retorna o valor descontado
(defn valor-descontado
  "retona o valor com desconto de 10% do valor bruto"
  [valor-bruto]
  (when (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (println "Calcula desconto de 10% de" valor-bruto "=" desconto)
      (- valor-bruto desconto))
    valor-bruto))

(valor-descontado 200)

; define uma function que retorna um bool
; para definir de vai aplicaro desconto ou nao
; esa function é passada com
; parametro para a function que aplica desconto
(defn mais-caro-que-100?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado-2
  "retona o valor com desconto de 10% do valor bruto"
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (println "Calcula desconto de 10% de" valor-bruto "=" desconto)
      (- valor-bruto desconto))
    valor-bruto))

; passa como primeiro parametro
; a function que define se deve aplicar desconto
; e o valor a ser aplicado o desconto
(valor-descontado-2 mais-caro-que-100? 150)

; Função anonima
; mesmo exemplo anterior, mas com function anonima
(valor-descontado-2 (fn [valor-bruto] > valor-bruto 100) 150)

; Função anonima 2
; # invoca fn anonima, % ou %1 primeiro arg, %2 segundo, %3...
(valor-descontado-2 #(> % 100) 150)

; define o symbol como uma lambda
(def valor-maior-que-100? #(> % 100))

; passa o simbulo def como lambda como parametro
(valor-descontado-2 valor-maior-que-100? 150)
