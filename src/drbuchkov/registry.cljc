(ns drbuchkov.registry)

(defonce ^:dynamic *registry* (atom {}))

(defn reg-set!
  "Sets in the sub-registry `subreg` the `key` to `value`."
  [subreg key value]
  (swap! *registry* assoc-in [subreg key] value))

(defn reg-get
  "Gets from the sub-registry `subreg` the `value` for `key`."
  [subreg key]
  (get-in @*registry* [subreg key]))

(defn reg-update!
  "Updates in the sub-registry `subreg` the value for `key` by applying `f` to it and any additional `args`."
  [subreg key f & args]
  (apply (partial swap! *registry* update-in [subreg key] f) args))

(defn reg-remove!
  "Removes from the sub-registry `subreg` the value for `key`."
  [subreg key]
  (swap! *registry* update subreg dissoc key))

(defn reg-clean
  "Cleans the global registry."
  []
  (reset! *registry* {}))

(defmacro with-registry
  "Executes body by binding the global registry to `registry`."
  [registry & body]
  `(binding [*registry* (atom ~registry)]
     ~@body))