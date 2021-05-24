(defproject mads "0.1.0-SNAPSHOT"

  :description "DevDays demo project"
  :url "http://example.com/FIXME"

  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.10.3"]]
  :source-paths []
  :resource-paths []

  :profiles
  {:repl              {:repl-options {:init-ns user}}

   :uberjar           {:aot         :all
                       :omit-source true}

   :service/dev       {:source-paths ["dev/src"]
                       :dependencies [[integrant/repl "0.3.2"]
                                      [hawk "0.2.11"]
                                      [eftest "0.5.9"]]}

   :service/base      {:source-paths ["shared"]
                       :dependencies [[duct/core "0.8.0"]
                                      [duct/module.logging "0.5.0"]]
                       :plugins      [[duct/lein-duct "0.12.2"]]
                       :middleware   [lein-duct.plugin/middleware]}

   :service/app       {:main           ^:skip-aot mads.main
                       :source-paths   ["services/app"]
                       :resource-paths ["resources/app" "dev/resources/app"]
                       :dependencies   [[http-kit "2.5.0"]
                                        [javax.servlet/servlet-api "2.5"]
                                        [metosin/reitit "0.5.6"]
                                        [com.github.seancorfield/next.jdbc "1.1.646"]
                                        [org.postgresql/postgresql "42.2.5"]
                                        [honeysql "1.0.461"]
                                        [nilenso/honeysql-postgres "0.4.112"]]}

   :service/moderator {:main           ^:skip-aot mads.main
                       :source-paths   ["services/moderator"]
                       :resource-paths ["resources/moderator" "dev/resources/moderator"]}

   :service/analytics {:main           ^:skip-aot mads.main
                       :source-paths   ["services/analytics"]
                       :resource-paths ["resources/analytics" "dev/resources/analytics"]}

   :service/search    {:main           ^:skip-aot mads.main
                       :source-paths   ["services/search"]
                       :resource-paths ["resources/search" "dev/resources/search"]}

   :app               [:service/dev :service/base :service/app]
   :moderator         [:service/dev :service/base :service/moderator]
   :analytics         [:service/dev :service/base :service/analytics]
   :search            [:service/dev :service/base :service/search]

   :frontend          {:plugins      [[lein-shadow "0.4.0"]]
                       :source-paths ["frontend/web" "shared"]
                       :dependencies [[thheller/shadow-cljs "2.12.5"]
                                      [applied-science/js-interop "0.2.7"]
                                      [reagent "1.0.0"]
                                      [re-frame "1.2.0"]]
                       :npm-deps     [[shadow-cljs "2.12.5"]
                                      [react "17.0.2"]
                                      [react-dom "17.0.2"]]
                       :shadow-cljs  {:nrepl  {:port 3333}
                                      :builds {:app {:target     :browser
                                                     :output-dir "public/js"
                                                     :asset-path "/js"
                                                     :modules    {:main {:entries [mads.core]}}
                                                     :devtools   {:after-load mads.core/on-update
                                                                  :http-root  "public"
                                                                  :http-port  3000}}}}}}

  :aliases
  {"run:app"         ["with-profile" "app" "repl"]
   "build:app"       ["with-profile" "app" "uberjar"]

   "run:moderator"   ["with-profile" "moderator" "repl"]
   "build:moderator" ["with-profile" "moderator" "uberjar"]

   "run:analytics"   ["with-profile" "analytics" "repl"]
   "build:analytics" ["with-profile" "analytics" "uberjar"]

   "run:search"      ["with-profile" "search" "repl"]
   "build:search"    ["with-profile" "search" "uberjar"]

   "run:frontend"    ["with-profile" "frontend" "shadow" "watch" "app"]})

