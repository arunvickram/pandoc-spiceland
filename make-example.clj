#!/usr/bin/env bb

(require '[babashka.process :refer [shell]])

(if (empty? *command-line-args*)
  (println "./make-example.clj <lang>")
  (let [lang (first *command-line-args*) ]
    (shell "./make.clj" ":lang" lang ":in" (str "examples/inputs/" lang ".md") ":out" (str "examples/outputs/" lang ".pdf"))))
