(ns jobagator.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.java.jdbc :as sql]))

(def db (env :database-url "postgres://localhost:5432/jobs"))

(defroutes app
  (GET "/" []
    (let [jobs (sql/query db "SELECT * FROM jobs")]
      {
        :status 200
        :headers {"Content-Type" "text/html"}
        :body   (->> jobs
                  (map #(str "<li><a href='" (:url %) "'>" (:title %) "</a></li>"))
                  clojure.string/join)
      }))
  (POST "/jobs" [title url]
    (let [[{:keys [:id]} & _] (sql/query db ["SELECT id FROM jobs WHERE url = ?" url])
          now                 (new java.sql.Timestamp (System/currentTimeMillis))]
      (if (nil? id)
        (sql/insert! db :jobs {:title title :url url :updated_at now})
        (sql/update! db :jobs {:updated_at now} ["id = ?" id])))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
