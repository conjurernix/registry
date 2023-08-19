# drbuchkov/registry

A dead simple library for Clojure that provides a global registry for associating keys with values, with support for
sub-registries.

## Usage

### Installing

Add the following coordinates in your `deps.edn`

```clojure
io.github.drbuchkov/registry {:mvn/version "0.1.0-SNAPSHOT"}
```

or in your `project.clj`

```clojure
[io.github.drbuchkov/registry "0.1.0-SNAPSHOT"]
```

### Require

```clojure
(ns my-namespace
  (:require [drbuchkov.registry :as reg]))
```

### Setting Values

Use `reg-set!` to associate a key with a value in a sub-registry:

```clojure
(reg/reg-set! :subregistry :key "value")
```

### Getting Values

Use `reg-get` to retrieve the value for a key from a sub-registry:

```clojure 
(reg/reg-get :subregistry :key) ; => "value"
```

### Updating Values

Use `reg-update!` to update the value for a key in a sub-registry by applying a function to it:

```clojure 
(reg/reg-set! :subregistry :key 10)
(reg/reg-update! :subregistry :key inc)
(reg/reg-get :subregistry :key) ; => 11
```

You can also provide additional arguments to the function:

```clojure 
(reg/reg-update! :subregistry :key + 10)
(reg/reg-get :subregistry :key) ; => 20 (assuming the original value was 10)
```

Removing Values
Use `reg-remove!` to remove the value for a key from a sub-registry:

```clojure 
(reg/reg-remove! :subregistry :key)
```

Cleaning the Registry
Use `reg-clean` to clean the global registry:

```clojure
(reg/reg-clean)
```

Working with a custom registry
Use `with-registry` macro to execute a body of code with a custom registry:

```clojure 
(reg/with-registry {:subregistry {:key "value"}})
(reg/reg-get :subregistry :key) ; => "value"
```

## Development

Run the project's tests (they'll fail until you edit them):

    $ clojure -T:build test

Run the project's CI pipeline and build a JAR (this will fail until you edit the tests to pass):

    $ clojure -T:build ci

This will produce an updated `pom.xml` file with synchronized dependencies inside the `META-INF`
directory inside `target/classes` and the JAR in `target`. You can update the version (and SCM tag)
information in generated `pom.xml` by updating `build.clj`.

Install it locally (requires the `ci` task be run first):

    $ clojure -T:build install

Deploy it to Clojars -- needs `CLOJARS_USERNAME` and `CLOJARS_PASSWORD` environment
variables (requires the `ci` task be run first):

    $ clojure -T:build deploy

Your library will be deployed to io.github.drbuchkov/registry on clojars.org by default.

## License

Copyright © 2023 Nikolas Pafitis

Distributed under the Eclipse Public License version 1.0.
