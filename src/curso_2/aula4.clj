(ns curso_2.aula4
  (:require [loja.db :as l.db]
            [loja.logic :as l.logic]))

; -----------------------
; Tabalhando com mapas 2
; -----------------------

; separando arquivo de logica em l.logic
; separando modelos de dados em l.db

(l.logic/resumo (l.db/todos-pedidos))

(let [resumo (l.logic/resumo (l.db/todos-pedidos))]
  (println "resumo" resumo)
  (println "ordenado por preco" (sort-by :preco resumo))
  (println "ordenado por preco decrescente" (reverse (sort-by :preco resumo)))
  (println "primeiro elemento" (first resumo))
  (println "ultimo elemento" (last resumo))
  (println "segundo elemento" (second resumo))
  (println "todo resto menos o primeiro" (rest resumo))
  (println "os proximos elementos depois do primeiro" (next resumo))
  (println "a contagem de elemento" (count resumo))
  (println "classe do resumo" (class resumo))
  (println "pego o elemento nth do resumo" (nth resumo 3))
  (println "pega os x primeiros elementos" (take 2 resumo))
  (println "quem gastou mais de 500" (filter #(> (:total %) 500) resumo))
  (println "alguem gastou mais de 500?" (some #(> (:total %) 500) resumo)))