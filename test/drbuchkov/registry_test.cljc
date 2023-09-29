(ns drbuchkov.registry-test
  (:require [clojure.test :refer :all]
            [drbuchkov.registry :as sut]))

(defn with-fresh-reg [f]
  (sut/with-registry {}
                     (f)))

(use-fixtures :each with-fresh-reg)


(deftest reg-init!-test
  (testing "Initializing a sub-registry"
    (sut/reg-init! :foo {})
    (is (= {} (get @sut/*registry* :foo)))))

(deftest reg-subreg-test
  (testing "Getting a sub-registry"
    (sut/reg-init! :foo {})
    (is (= {} (sut/reg-subreg :foo)))))

(deftest reg-set!-test
  (testing "Setting and reading a value in the registry"
    (sut/reg-set! :foo :bar :baz)
    (is (= :baz (sut/reg-get :foo :bar)))
    (sut/reg-set! :foo [:bar :baz] :quux)
    (is (= :quux (sut/reg-get :foo [:bar :baz])))))

(deftest reg-update!-test
  (testing "Updating a value in the registry"
    (sut/reg-set! :foo :bar 1)
    (sut/reg-update! :foo :bar inc)
    (is (= 2 (sut/reg-get :foo :bar))))
  (testing "Updating a value in the registry with an fn that takes multiple arguments"
    (sut/reg-set! :foo :bar 1)
    (sut/reg-update! :foo :bar + 2 3)
    (is (= 6 (sut/reg-get :foo :bar)))))

(deftest reg-remove!-test
  (testing "Removing a value from the registry"
    (sut/reg-set! :foo :bar :baz)
    (sut/reg-remove! :foo :bar)
    (is (nil? (sut/reg-get :foo :bar)))))

(deftest reg-clean-test
  (testing "Cleaning the registry"
    (sut/reg-set! :foo :bar :baz)
    (sut/reg-clean)
    (is (= {} @sut/*registry*))))
