(ns core
  (:require [clojure.string :as str]
            [clj-http.client :as client]))

;; https://haveibeenpwned.com/Passwords
;; > 17727EAB0E800E62A776C76381DEFBC4145:4163

(def horse "correcthorsebatterystaple")

(defn sha1 [^String s]
  (->>
    (.getBytes s)
    (.digest (java.security.MessageDigest/getInstance "SHA"))
    (map #(format "%02X" %))
    (apply str)))

(sha1 horse)

(defn pwned-range [^String prefix]
  (->>
    (str "https://api.pwnedpasswords.com/range/" prefix)
    client/get
    :body
    (str/split-lines)))

(defn pwned [^String hash]
  (let [prefix (subs hash 0 5)
        suffix (subs hash 5)]
    (->>
      (pwned-range prefix)
      (filter #(str/starts-with? % suffix)))))


(->>
  horse
  sha1
  pwned)