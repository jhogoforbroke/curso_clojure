(ns curso_2.aula3
  (:require [curso_2.db :as loja.db]))

; --------------------
; Tabalhando com mapas
; --------------------

(def todos-pedidos (loja.db/todos-pedidos))

(println todos-pedidos)
; [{:usuario 1, :items {:mochila {:id :mochila...

; ------------
; group-by
; ------------
;Pedidos agrupados por usuarios
(println (group-by :usuario todos-pedidos))
; {1 [{:usuario 1, :items {:mochila {:id :mo...

(println (vals (group-by :usuario todos-pedidos)))
; ([{:usuario 1, :items {:mochila {:id :m

; Quanrtidade de Pedidos por usuario
(println (map count (vals (group-by :usuario todos-pedidos))))

; threading last
(->> todos-pedidos
     (group-by :usuario)
     vals
     (map count))
; ------------


(defn total-do-item
  "recebe um item e devolver o preco total (quantidade * preco)"
  [item]
  (let [quantidade (get item :quantidade 0)                 ; pega quantidade de itens se nao tiver default 0
        preco (get item :preco 0)]                          ; pega preço do item se nao tiver default 0
    (* quantidade preco)))                                  ; retorna quantide X preço

(defn total-dos-items
  "soma dos valores totals dos itens de um pedido"
  [pedido]
  (let [items (get pedido :items)]                          ; pega todos itens de um pedido
    ; para cada item soma o valor acumulado
    ; ao valor total do proximo item e retorna total final
    (reduce + (map total-do-item (vals items)))))

(defn total-pedido
  "soma total do pedido"
  [pedido]
  ; para cada pedido soma o valor acumulado
  ; ao valor total de um pedido e reotna o total final
  (reduce + (map total-dos-items pedido)))

(defn detalhes-do-pedido
  "retorna hashmap de informações detalhadas de um pedido"
  [[usuario, pedido]]
  {:usuario usuario
   :pedidos (count pedido)
   :total   (total-pedido pedido)})

(defn resumo
  "retorna resumo dos pedidos"
  [pedidos]
  (->> pedidos
       (group-by :usuario)
       (map detalhes-do-pedido)))

(resumo todos-pedidos)
; ({:usuario 1, :pedidos 2, :total 480}
;  {:usuario 2, :pedidos 2, :total 686}
;  {:usuario 3, :pedidos 1, :total 106}
;  {:usuario 4, :pedidos 1, :total 118})
