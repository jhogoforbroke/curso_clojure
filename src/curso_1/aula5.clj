(ns curso_1.aula5)

; --------
; Trabalhando com HashMaps
; --------

; definindo um mapa com mapas de props
(def pedido {:mochila  {:quantidade 10, :preco 30}
             :camiseta {:quantidade 30, :preco 20}})
; keys
(keys pedido)
(keys (:mochila pedido))

; values
(vals pedido)
(vals (:mochila pedido))

; formas de recuperar o valor dentro do mapa
(get pedido :mochila)
(:mochila pedido)
(:quantidade (:mochila pedido))

; assoc muda valor de um mapa
(assoc-in pedido [:mochila :quantidade] "BANANA")

; como aplicar uma fn dentro de valores de um mapa
(update-in pedido [:mochila :quantidade] inc)

; THREADING FIRST
(-> pedido
    :mochila
    :quantidade)

