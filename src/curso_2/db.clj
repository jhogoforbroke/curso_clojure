(ns curso_2.db)

(def pedido1 {:usuario 1
              :items   {:mochila  {:id :mochila, :quantidade 3, :preco 30}
                        :camiseta {:id :camiseta, :quantidade 4, :preco 30}
                        :chaveiro {:id :chaveiro, :quantidade 1}}})

(def pedido2 {:usuario 1
              :items   {:mochila  {:id :mochila, :quantidade 2, :preco 30}
                        :camiseta {:id :camiseta, :quantidade 7, :preco 30}
                        :chaveiro {:id :chaveiro, :quantidade 1}}})

(def pedido3 {:usuario 2
              :items   {:mochila  {:id :mochila, :quantidade 1, :preco 80}
                        :camiseta {:id :camiseta, :quantidade 1, :preco 30}
                        :chaveiro {:id :chaveiro, :quantidade 1}}})

(def pedido4 {:usuario 2
              :items   {:mochila  {:id :mochila, :quantidade 6, :preco 80}
                        :camiseta {:id :camiseta, :quantidade 8, :preco 12}
                        :chaveiro {:id :chaveiro, :quantidade 1}}})

(def pedido5 {:usuario 3
              :items   {:mochila  {:id :mochila, :quantidade 2, :preco 22}
                        :camiseta {:id :camiseta, :quantidade 2, :preco 31}
                        :chaveiro {:id :chaveiro, :quantidade 1}}})

(def pedido6 {:usuario 4
              :items   {:mochila  {:id :mochila, :quantidade 1, :preco 83}
                        :camiseta {:id :camiseta, :quantidade 1, :preco 35}
                        :chaveiro {:id :chaveiro, :quantidade 1}}})

(defn todos-pedidos []
  [pedido1, pedido2, pedido3, pedido4, pedido5, pedido6])