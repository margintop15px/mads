(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [fipp.edn :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [duct.core :as duct]
            [duct.core.repl :as duct-repl :refer [auto-reset]]
            [eftest.runner :as eftest]
            [integrant.core :as ig]
            [integrant.repl :refer [clear halt go init prep reset]]
            [integrant.repl.state :refer [config system]]))


(def profiles
  [:duct.profile/dev :duct.profile/local])


(defn read-config [service]
  (duct/read-config (io/resource (str service "/config.edn"))))


(defn init-service [service]
  (clojure.tools.namespace.repl/set-refresh-dirs
   "dev/src"
   (str "services/" service))

  (when (io/resource "local.clj")
    (load "local"))

  (integrant.repl/set-prep!
   #(duct/prep-config (read-config service) profiles)))


(duct/load-hierarchy)


(comment

 (init-service "app")

 (go)
 (reset)
 (auto-reset)

 nil)


