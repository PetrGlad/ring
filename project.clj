(defproject ring "1.2.0-beta1"
  :description "A Clojure web applications library."
  :url "https://github.com/ring-clojure/ring"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies
    [[ring/ring-core "1.2.0-beta1"]
     [ring/ring-devel "1.2.0-beta1"]
     [ring/ring-jetty-adapter "1.2.0-beta1"]
     [ring/ring-servlet "1.2.0-beta1"]]
  :plugins
    [[lein-sub "0.2.1"]
     [codox "0.6.3"]]
  :sub
    ["ring-core"
     "ring-devel"
     "ring-jetty-adapter"
     "ring-servlet"]
  :codox
    {:src-dir-uri "http://github.com/ring-clojure/ring/blob/1.1.8"
     :src-linenum-anchor-prefix "L"
     :sources ["ring-core/src"
               "ring-devel/src"
               "ring-jetty-adapter/src"
               "ring-servlet/src"]})
