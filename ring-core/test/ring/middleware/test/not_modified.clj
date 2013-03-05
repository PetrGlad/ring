(ns ring.middleware.test.not-modified
  (:use clojure.test
        ring.middleware.not-modified))

(defn- handler-etag [etag]
  (constantly
   {:status 200
    :headers {"etag" etag}
    :body ""}))

(defn- handler-modified [modified]
  (constantly
   {:status 200
    :headers {"last-modified" modified}
    :body ""}))

(defn- etag-request [etag]
  {:headers {"if-none-match" etag}})

(defn- modified-request [modified-date]
  {:headers {"if-modified-since" modified-date}})

(deftest test-wrap-not-modified
  (with-redefs [ring.middleware.not-modified/not-modified-response #(vector %1 %2)]
    (let [req (modified-request "Sun, 23 Sep 2012 11:00:00 GMT")
          handler (handler-modified "Jan, 23 Sep 2012 11:00:00 GMT")]
      (is (= [(handler req) req] 
            ((wrap-not-modified handler) req))))))

(deftest test-not-modified-response  
  (testing "etag match"
    (let [request-etag "match"
          req {:headers {"if-none-match" request-etag}}
          handler-resp #(hash-map :status 200 :headers {"etag" %} :body "")]
      (is (= 304 (:status (not-modified-response (handler-resp request-etag) req))))
      (is (= 200 (:status (not-modified-response (handler-resp "laskdhfskljahdflksjdhf") req))))))
    
  (testing "not modified"
    (let [req #(hash-map :headers {"if-modified-since" %})
          last-modified "Sun, 23 Sep 2012 11:00:00 GMT"
          h-resp {:status 200 :headers {"last-modified" last-modified} :body ""}]
      (is (= 304 (:status (not-modified-response h-resp (req last-modified)))))
      (is (= 304 (:status (not-modified-response h-resp (req "Sun, 23 Sep 2012 11:52:50 GMT")))))
      (is (= 200 (:status (not-modified-response h-resp (req "Sun, 23 Sep 2012 10:00:50 GMT")))))))
  
  (testing "no modification info"
    (let [response {:status 200 :headers {} :body ""}]
      (is (= 200 (:status (not-modified-response response (etag-request "\"12345\"")))))
      (is (= 200 (:status (not-modified-response response (modified-request "Sun, 23 Sep 2012 10:00:00 GMT"))))))))
