(ns mads.server
  (:require [integrant.core :as ig]
            [org.httpkit.server :as http-kit]))


(defmethod ig/init-key :mads/server [_ {:keys [port handler]}]
  (let [server (http-kit/run-server handler {:port port})]
    (println "Running server on port " port)
    server))


(defmethod ig/halt-key! :mads/server [_ server]
  (when (some? server)
    (server)))
