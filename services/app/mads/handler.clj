(ns mads.handler
  (:require [integrant.core :as ig]
            [reitit.core :as reitit]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [muuntaja.core :as m]
            [mads.ad-store :as ad.store]))


(defn process-ad
  "Store ad record in the database"
  [{{{:keys [text author]} :body} :parameters
    {{:keys [context]} :data}     ::reitit/match}]
  (let [db        (:db context)
        ad-data   {:text text :author author}
        ad-record (ad.store/create-record db ad-data)]
    {:status 200
     :body   ad-record}))


(defmethod ig/init-key :mads/handler [_ {:keys [db]}]
  (ring/ring-handler
   (ring/router
    ["/api" {:context {:db db}}
     ["/ad" {:post {:parameters {:body {:text string? :author string?}}
                    :handler    process-ad}}]]

    {:data {:coercion   reitit.coercion.spec/coercion
            :muuntaja   m/instance
            :middleware [muuntaja/format-negotiate-middleware
                         muuntaja/format-request-middleware
                         rrc/coerce-exceptions-middleware
                         rrc/coerce-request-middleware
                         rrc/coerce-response-middleware]}})))
