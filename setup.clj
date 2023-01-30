#!/usr/bin/env bb

(require '[babashka.process :refer [shell]]
         '[babashka.fs :as fs]
         '[clojure.string :as str])

(def texmf-dir
  (-> (shell {:out :string} "kpsewhich -var-value=TEXMFHOME")
      :out
      str/split-lines
      first))

(def babel-dir (str texmf-dir "/tex/latex/babel"))

(when-not (fs/exists? babel-dir)
  (fs/create-dirs babel-dir))

(fs/delete-if-exists (str babel-dir "/locale"))
(fs/create-sym-link (str babel-dir "/locale") (fs/absolutize "locale"))