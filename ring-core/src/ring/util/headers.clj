(ns ring.util.headers)

(defn get-header 
  "Get request or response header by name.
   Lookup is case-insensitive"
  [message header-name]
  (let [normalized-name (.toLowerCase header-name)]
    (-> (filter
          #(= (-> % first .toLowerCase) normalized-name) 
          (:headers message))
      first
      second)))