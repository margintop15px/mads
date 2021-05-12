(ns mads.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [mads.components.app :refer [app]]))


(def development?
  ^boolean goog.DEBUG)


(defn mount []
  (rd/render
   [app]
   js/app))


(defn on-update []
  (mount))


(defn ^:export main []
  (when development?
    (enable-console-print!))

  (mount))
