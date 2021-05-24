(ns mads.components.app)


(defn navigation []
  [:div.container
   [:header.d-flex.flex-wrap.align-items-center.justify-content-center.justify-content-md-between.py-3.mb-2.border-bottom
    [:ul.nav.col-12.mb-2.justify-content-center.mb-md-0
     [:li [:a.nav-link.px-2.link-secondary {:href "#"} "Find Ads"]]
     [:li [:a.nav-link.px-2.link-dark {:href "#"} "Publish"]]]]])


(defn search []
  [:header {:class "py-4 px-4 mb-4"}
   [:div {:class "container-fluid d-grid gap-3 align-items-center"}
    [:div {:class "d-flex align-items-center"}
     [:form {:class "w-100"}
      [:input
       {:class       "form-control"
        :aria-label  "Search",
        :placeholder "Search...",
        :type        "search"}]]]]])


(defn card []
  [:div.card.mb-3
   [:div.row.g-0
    [:div.col-md-4
     [:svg.bd-placeholder-img
      {:focusable           "false",
       :preserveaspectratio "xMidYMid slice",
       :aria-label          "Placeholder: Image",
       :role                "img",
       :height              "180",
       :width               "100%"}
      [:title "Placeholder"]
      [:rect {:fill "#868e96", :height "100%", :width "100%"}]
      [:text {:dy ".3em", :fill "#dee2e6", :y "50%", :x "50%"} "Image"]]]
    [:div.col-md-8
     [:div.card-body
      [:h5.card-title "Card title"]
      [:p.card-text
       "This is a wider card with supporting text below as a natural lead-in to additional content.
        This content is a little bit longer."]
      [:p.card-text
       [:small.text-muted "Published 3 mins ago"]]]]]])


(defn loader []
  [:div.text-center
   [:div.spinner-grow.text-secondary.my-4
    {:role "status", :style {:width "3rem" :height "3rem"}}
    [:span.visually-hidden "Loading..."]]])


(defn app []
  [:<>
   [navigation]
   [search]
   [:div.container
    [card]
    [card]]

   [loader]])
