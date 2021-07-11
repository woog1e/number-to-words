(ns woog1e.number-to-words.polish)

(def ^:private n-0-9 ["zero" "jeden" "dwa" "trzy" "cztery" "pięć" "sześć" "siedem" "osiem" "dziewięć"])

(def ^:private n-10 ["" "" "dwadzieścia" "trzydzieści" "czeterdzieści" "pięćdziesiąt" "sześćdziesiąt" "siedemdziesiąt"
                     "osiemdziesiąt" "dziewięćdziesiąt"])

(def ^:private n-10-19 ["dziesięć" "jedenaście" "dwanaście" "trzynaście" "czternaście" "piętnaście" "szesnaście"
                        "siedemnaście" "osiemnaście" "dziewiętnaście"])

(def ^:private n-100 ["" "sto" "dwieście" "trzysta" "czterysta" "pięćset" "sześćset" "siedemset" "osiemset" "dziewięćset"])

(def ^:private types [[nil nil nil]
                      ["tysiąc" "tysiące" "tysięcy"]
                      ["milion" "miliony" "milionów"]
                      ["miliard" "miliardy" "miliardów"]
                      ["bilion" "biliony" "bilionów"]
                      ["biliard" "biliardy" "biliardów"]
                      ["trylion" "tryliony" "trylionów"]])

(defn- divide-mod [number divisor]
  (int (mod (/ number divisor) divisor)))

(defn- get-suffix [number type]
  (cond
    (= 1 number) (nth type 0)
    (and (< (mod number 100) 20) (> (mod number 100) 10)) (nth type 2)
    (and (> (mod number 10) 1) (< (mod number 10) 5)) (nth type 1)
    (> number 0) (nth type 2)))

(defn- get-tens [number]
  (if (and (>= number 10) (<= number 19))
    (nth n-10-19 (mod number 10))
    (nth n-10 (divide-mod number 10))))

(defn- get-single [singles tens hundreds type]
  (cond
    (and (not= tens 1) (empty? (first type)) (>= singles 1)) (nth n-0-9 singles)
    (and (= tens 0) (or (= singles 1) (= singles 0))) nil
    (and (= tens 0) (> singles 1)) (nth n-0-9 singles)
    (and (>= tens 2) (>= singles 1)) (nth n-0-9 singles)))

(defn- number-to-text [number type]
  (let [singles (mod number 10)
        hundreds (divide-mod number 100)
        tens (divide-mod (mod number 100) 10)]
    [(nth n-100 hundreds)
     (get-tens (+ singles (* tens 10)))
     (get-single singles tens hundreds type)
     (get-suffix number type)]))

(defn ->polish-number-to-words [number]
  (if (= number 0)
    (nth n-0-9 0)
    (->>
     (re-seq #"\d{1,3}" (clojure.string/join (reverse (str number))))
     (map-indexed (fn [idx number-part]
                    (number-to-text
                     (Integer/parseInt (clojure.string/join (reverse number-part)))
                     (nth types idx))))
     (reverse)
     (flatten)
     (filter #(not (clojure.string/blank? %)))
     (clojure.string/join " "))))
