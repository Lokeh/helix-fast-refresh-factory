(ns app.core
  "This namespace contains your application and is the entrypoint for 'yarn start'."
  (:require
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    [helix.experimental.refresh :as r]
    [helix.hooks :as hooks]
    ["react-dom" :as rdom]))

(defnc test-component
  [props]
  {:helix/features {:define-factory true
                    :fast-refresh true}}
  (let [[count set-count] (hooks/use-state 0)]
    (d/div
     (d/p "some text" " " count)
     (d/button {:on-click #(set-count inc)} "inc"))))

(def state (atom {:some "value"}))

(defn ^:dev/after-load refresh
  "Render the toplevel component for this app."
  []
  (r/refresh!))


(defn ^:export main
  "Run application startup logic."
  []
  (r/inject-hook!)
  (rdom/render
   (test-component {:foo "bar"})
   (.getElementById js/document "app")))
