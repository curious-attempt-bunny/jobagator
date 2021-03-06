(defproject jobagator "0.0.1"
  :main jobagator.web
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "0.5.0"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [postgresql "9.1-901-1.jdbc4"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "app-standalone.jar"
  :profiles {:dev  {:env {:database-url "postgres://localhost:5432/jobs"}
                    :jvm-opts [ "-Dextensions.dir=./newrelic/extensions"
                                "-javaagent:./newrelic/newrelic.jar"]}})
