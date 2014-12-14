```
createdb jobs
psql -d jobs
create table jobs (
    id integer PRIMARY KEY,
    title varchar(200) NOT NULL,
    url varchar(500) NOT NULL
);
lein repl
(require 'clojure-getting-started.web)
(def server (clojure-getting-started.web/-main))
```