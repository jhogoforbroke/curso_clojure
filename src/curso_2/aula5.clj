(ns curso_2.aula5)

; --------------------------
; Comportamento EAGER e LAZY
; --------------------------
; EAGER - guloso/ansioso executa para todos os elementos
; LAZY - preguiçoso executa somente para os elementos que foram invocados

; comportamento EAGER - lista encadeada
(println '(1 2 3 4 5 6 7 8 9))
; (1 2 3 4 5 6 7 8 9)

; comportamento LAZY - lista
(println (take 2 (range 100000000000000000000000000000000000000000000000000)))
; (0 1)

(defn printa1
  [elemento]
  (println "print 1 =>" elemento)
  elemento)

(defn printa2
  [elemento]
  (println "print 2 =>" elemento)
  elemento)

; LAZY (chunks) - lista com map
; Executa em CHUNKS de 31 elementos
(->> (range 60)
     (map printa1)
     (map printa2)
     println)
; print 1 => 0
; print 1 => 1
; ...
; print 1 => 31
; print 2 => 0
; print 2 => 1
; ...
; print 2 => 31
; print 1 => 32
; print 1 => 33
; ...
; print 1 => 59
; print 2 => 32
; print 2 => 33
; ...
; print 2 => 59



; EAGER - mapv
; Transforma a lista em um vetor(eager),
; que fica instanciado por padrão totalmente na memoria
(->> (range 60)
     (mapv printa1)
     (mapv printa2)
     println)
; print 1 => 0
; print 1 => 1
; print 1 => 2
; ...
; print 1 => 59
; print 2 => 0
; print 2 => 1
; print 2 => 2
; ...
; print 2 => 59



; EAGER CHUNKED
; map em array
; Executa a cada 32 elementos
(->> [ 1 2 3 4 5 6 7 8 9 10
        11 12 13 14 15 16 17 18 19 20
        21 22 23 24 25 26 27 28 29 30
        31 32 33 34 35 36 37 38 39 40
        41 42 43 44 45 46 47 48 49 50]
     (map printa1)
     (map printa2)
     println)
; print 1 => 0
; print 1 => 1
; ...
; print 1 => 32
; print 2 => 0
; print 2 => 1
; ...
; print 2 => 32
; print 1 => 33
; print 1 => 34
; ...
; print 1 => 59
; print 2 => 33
; print 2 => 34
; ...
; print 2 => 59



; LAZY
; map na lista encadeada
; Executa a cada elemento
(->> '( 1 2 3 4 5 6 7 8 9 10
        11 12 13 14 15 16 17 18 19 20
        21 22 23 24 25 26 27 28 29 30
        31 32 33 34 35 36 37 38 39 40
        41 42 43 44 45 46 47 48 49 50)
     (map printa1)
     (map printa2)
     println)
; print 1 => 1
; print 2 => 1
; print 1 => 2
; print 2 => 2
; ...
; print 1 => 49
; print 2 => 49
; print 1 => 50
; print 2 => 50
