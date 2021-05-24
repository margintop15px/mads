(ns mads.ad-store
  (:require [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [honeysql-postgres.helpers :as psqlh]
            [next.jdbc :as jdbc])
  (:import [mads.db DB]))


(defprotocol AdStore
  (create-record [this ad])
  (set-status [this ad-id status]))


(extend-protocol AdStore
  DB
  (create-record [{:keys [db]} {:keys [text author]}]
    (let [command (-> (h/insert-into :ads)
                      (h/values [{:text text :author author :status "pending"}])
                      (psqlh/returning :*)
                      sql/format)
          ds      (jdbc/get-datasource db)]
      (-> (jdbc/execute! ds command)
          first)))

  (set-status [{:keys [db]} ad-id status]
    (let [command (-> (h/update :ads)
                      (h/sset {:status status})
                      (h/where [:= :id ad-id])
                      sql/format)
          ds      (jdbc/get-datasource db)]
      (jdbc/execute! ds command))))
