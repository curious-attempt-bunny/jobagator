```
createdb jobs
psql -d jobs
create table jobs (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    url VARCHAR(500) NOT NULL UNIQUE,
    updated_at TIMESTAMP NOT NULL
);
lein repl
(require 'jobagator)
(def server (jobagator/-main))
```