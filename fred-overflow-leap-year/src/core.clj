(ns core
  (:require
    [clojure.test :refer [deftest is are run-tests]]))

; tropical year: -365.2422 days

(* 0.25 100)                                                ; 25 leap years
; |...
(* 0.24 400)                                                ; 96
; ....|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...
(* 0.2425 400)                                              ;; 97

;; todo how many years would it take for 0.2425-0.2422 to become significant?


(defn not-divisible? [x y]
  (not= 0 (rem x y)))

(defn leap-year? [year]
  (cond
    (not-divisible? year 4) false
    (not-divisible? year 100) true
    (not-divisible? year 400) false
    :else true))

;; todo use bits to compress from 400 to 25
(def leap-year-string "|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|.......|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|.......|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|.......|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...|...")

(defn leap-year? [year]
  (let [index (rem year 400)
        ch (.charAt leap-year-string index)]
    (= \| ch)))

(deftest are-leap-years
  (are [year] (leap-year? year)
              1600
              1980 1984 1988 1992 1996
              2000
              2004 2008 2012 2016
              2400))

(deftest are-common-years
  (are [year] (not (leap-year? year))
              1700 1800 1900
              1997 1998 1999
              2001 2002 2003
              2100 2200 2300))

(run-tests)