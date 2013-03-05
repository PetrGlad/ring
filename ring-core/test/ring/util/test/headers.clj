(ns ring.util.test.headers
  (:use ring.util.headers
        clojure.test))

(deftest test-get-header
  (is (= "a" (get-header {:headers {"Some-Header" "a"}} "some-header")))
  (is (nil? (get-header {:headers {"Same-Hero" "a"}} "some-header"))))