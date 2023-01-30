#!/usr/bin/env bb

(require '[babashka.cli :as cli]
         '[babashka.process :refer [shell]]
         '[babashka.fs :as fs])

;; TODO check whether or not l3install contributed to the installation of in the local tex env
; this contains any supported but not necessarily localized languages
(def supported-languages
  ["assamese"
   "bangla"
   "gondi/adilabad"
   "gondi/aheri"
   "gujarati"
   "hindi"
   "ho"
   "kannada"
   "limbu"
   "malayalam"
   "marathi"
   "meitei"
   "odia"
   "punjabi/gurmukhi"
   "santali"
   "sinhala"
   "sora"
   "tamil"
   "telugu"
   "tibetan"
   "urdu"])

;; TODO: add command to link locales with texmf folder
(def spec [[:defaults {:ref "<defaults>"
                       :desc "A replacement defaults.yaml file if you want to use a different defaults file"
                       :alias :D
                       :default "defaults.yaml"}]
           [:lang {:ref "<language>"
                   :desc "The language of the markdown file. Check README.md to see which ones are supported and semi-supported"
                   :alias :L
                   :require true}]
           [:dir {:ref "<dir>"
                  :desc "Optional working documents directory. This way you don't have to specify the directory in the input and output"
                  :alias "d"
                  :default "."}]
           [:in {:ref "<file>"
                 :desc "The name of the markdown file that will be compiled"
                 :alias :i
                 :require true}]
           [:out {:ref "<file>"
                  :desc "The name of the pdf file that will be generated"
                  :alias :o
                  :default "document.pdf"}]])

(defn handle-error [{:keys [spec type cause msg option] :as data}]
  (if (= type :org.babashka/cli)
    (case cause
      :restrict
      (println
        (format "Must choose a supported or semi-supported language. Go to langs"))
      (println msg))
    (throw (ex-info msg data)))
  (System/exit 1))

(if (empty? *command-line-args*)
  (do
    (println "./make.clj <options>:")
    (println (cli/format-opts {:spec spec})))
  (let [options (cli/parse-opts *command-line-args* {:spec spec :error-fn handle-error})
        lang-dir (str "langs/" (:lang options))
        defaults (str "--defaults=" (:defaults options))
        result (shell "pandoc" defaults (str lang-dir "/prelude.md") (str (:dir options) "/" (:in options)) "-o" (str (:dir options) "/" (:out options)))]
    (when (zero? (:exit result))
      (println (str "Successfully generated a pdf at: " (:out options))))))
