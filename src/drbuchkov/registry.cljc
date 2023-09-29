(ns drbuchkov.registry)

(defonce ^:dynamic *registry* (atom {}))

(defn reg-init!
  "Initializes the sub-registry `subreg` with the value `value` registry."
  [subreg value]
  (swap! *registry* assoc subreg value))

(defn reg-subreg [subreg]
  "Gets the sub-registry `subreg` from the global registry."
  (get-in @*registry* [subreg]))

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