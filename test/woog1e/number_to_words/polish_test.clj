(ns woog1e.number-to-words.polish-test
  (:require [woog1e.number-to-words.polish :as ntwp]))

(def test-data [[0 "zero"]
                [1 "jeden"]
                [5 "pięć"]
                [14 "czternaście"]
                [10 "dziesięć"]
                [1000 "tysiąc"]
                [114000 "sto czternaście tysięcy"]
                [124000 "sto dwadzieścia cztery tysiące"]
                [121000 "sto dwadzieścia jeden tysięcy"]
                [121001 "sto dwadzieścia jeden tysięcy jeden"]
                [100000 "sto tysięcy"]
                [1000000 "milion"]
                [2000 "dwa tysiące"]
                [24000 "dwadzieścia cztery tysiące"]
                [123456789 "sto dwadzieścia trzy miliony czterysta pięćdziesiąt sześć tysięcy siedemset osiemdziesiąt dziewięć"]
                [987654321 "dziewięćset osiemdziesiąt siedem milionów sześćset pięćdziesiąt cztery tysiące trzysta dwadzieścia jeden"]
                [113000 "sto trzynaście tysięcy"]
                [11 "jedenaście"]
                [112515 "sto dwanaście tysięcy pięćset piętnaście"]
                [1001001 "milion tysiąc jeden"]
                [4137 "cztery tysiące sto trzydzieści siedem"]
                [111222333444 "sto jedenaście miliardów dwieście dwadzieścia dwa miliony trzysta trzydzieści trzy tysiące czterysta czeterdzieści cztery"]])

(clojure.test/deftest number-test
  (doseq [[number expected] test-data]
    (clojure.test/is (= expected (ntwp/->polish-number-to-words number)))))

(clojure.test/deftest pre-condition-test
  (clojure.test/is (thrown? java.lang.AssertionError (ntwp/->polish-number-to-words 1.1)))
  (clojure.test/is (thrown? java.lang.AssertionError (ntwp/->polish-number-to-words \c)))
  (clojure.test/is (thrown? java.lang.AssertionError (ntwp/->polish-number-to-words ""))))
