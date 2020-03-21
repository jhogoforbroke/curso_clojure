(ns curso_1.aula6)

; --------
; Trabalhando com HashMaps
; --------

(def pedido {:mochila  {:quantidade 13, :preco 30}
             :camiseta {:quantidade 30, :preco 20}
             :chaveiro {:quantidade 1, :preco 0}})

; --------
; destruction
; --------
; destruction mapa passando a chave e valor
(defn imprime-chave-e-valor [[chave valor]]
  (println "chave" chave)
  (println "valor" valor))


; --------
; MAP
;----------------------------------------
; map chama sua funcao (imprime-chave-e-valor)
; passando cada parametro do hashmap
; como chave e valor via destruction [[chave valor]]
(map imprime-chave-e-valor pedido)

(defn preco-por-produto [[_ value]]
  (* (:quantidade value) (:preco value)))

; preco de cada produto
; (300, 600)
(map preco-por-produto pedido)


; --------
; Reduce
; ---------------------------------------
; total do pedido
; soma dos precos dos produtos
(reduce + (map preco-por-produto pedido))

; total do pedido como function
(defn total-do-pedido
  [pedido]
  (reduce + (map preco-por-produto pedido)))

(total-do-pedido pedido)

((fn [pedido] (reduce + (map preco-por-produto pedido))) pedido)
(#(reduce + (map preco-por-produto %)) pedido)


; --------
; THREAD LAST
; ---------------------------------------
; (->> [arg]
;      (fn [arg])
;      (fn [arg]))
(defn total-do-pedido-tl
  [pedido]
  (->> pedido
       (map preco-por-produto)
       (reduce +)))

(total-do-pedido-tl pedido)

; trabalhando com mapa vals
(defn preco-total-do-produto
  [item]
  (* (:quantidade item) (:preco item)))

(defn total-do-pedido-tl-val
  [pedido]
  (->> pedido
       vals
       (map preco-total-do-produto)
       (reduce +)))

(total-do-pedido-tl-val pedido)


; --------
; FILTER
;----------------------------------------
; filter com funcao que faz destruction
(defn gratuito-destruction?
  [[_ item]]
  (<= (get item :preco 0) 0))

(filter gratuito-destruction? pedido)

; filter com funcao que recebe o item partindo de uma
; funcao anonima que passa por parametro somente o item
(defn item-gratuito?
  [item]
  (<= (get item :preco 0) 0))

(filter (fn [[_ item]] (item-gratuito? item)) pedido)

(filter
  (fn [[_ item]]
    (<= (get item :preco 0) 0))
  pedido)

; versao com lambda no lugar da fn anonima
; que pega passa somente o item "segundo elemento do hashmap"
(filter #(item-gratuito? (second %)) pedido)

; not para inverter o valor (!)
(defn pago?
  [item]
  (not (item-gratuito? item)))

(pago? {:preco 10})
(pago? {:preco 0})

; comp faz composicao de funcoes
; para serem executadas
; compondo a function "not" com a function "gratiuto?"
(def pago-comp? (comp not item-gratuito?))

(pago-comp? {:preco 10})
(pago-comp? {:preco 0})