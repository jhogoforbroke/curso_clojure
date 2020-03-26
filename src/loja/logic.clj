(ns loja.logic)

(defn total-do-item
  "recebe um item e devolver o preco total (quantidade * preco)"
  [item]
  (let [quantidade (get item :quantidade 0)
        preco (get item :preco 0)]
    (* quantidade preco)))

(defn total-dos-items
  "soma dos valores totals dos itens de um pedido"
  [pedido]
  (let [items (get pedido :items)]
    (reduce + (map total-do-item (vals items)))))

(defn total-pedido
  "soma total do pedido"
  [pedido]
  (reduce + (map total-dos-items pedido)))

(defn detalhes-do-pedido
  "retorna hashmap de informações detalhadas de um pedido"
  [[usuario, pedido]]
  {:usuario usuario
   :pedidos (count pedido)
   :total (total-pedido pedido)})

(defn resumo
  "retorna resumo dos pedidos"
  [pedidos]
  (->> pedidos
       (group-by :usuario)
       (map detalhes-do-pedido)))