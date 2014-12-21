(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.java.jdbc :as sql]))

(def db (env :database-url "postgres://localhost:5432/jobs"))

(defroutes app
  (GET "/jobs" []
    {
      :status  200
      :headers {"Content-Type" "text/plain"}
      :body    (sql/query db "SELECT * FROM jobs")
    })
  (POST "/jobs" [title url]
    (sql/insert! db
      :jobs {:title title :url url})))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
