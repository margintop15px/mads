(ns mads.db
  (:require [integrant.core :as ig]))


(defrecord DB [db])


(defmethod ig/init-key :mads/db [_ options]
  (->DB options))
