# Crawler

Categorizes the Welt RSS Feed and saves it in a postgres Database.

Backend for https://github.com/jpreuss00/newsPaperApp

## Built With

* [Java]

## How to use

1: Start a postgres Docker Container:
* docker pull postgres
* docker run -p 5432:5432 -e POSTGRES_PASSWORD="choose a password" -d postgres

2: Start your application:
* docker run --env DBHOST="IP adress of your Docker Container" --env DBUSER=postgres --env DBPWD="your choosen password" --env DB="your database name" -d "Image name"

3: Connect to your Postgres Database:
* psql -h localhost -U postgres -d postgres
* or open it in your webbrowser with PG Admin 4 or your common IDE

## Links

* https://thawing-ridge-63424.herokuapp.com/
* https://thawing-ridge-63424.herokuapp.com/health/
* https://thawing-ridge-63424.herokuapp.com/api/pingpong/ (?echo=...)
* https://thawing-ridge-63424.herokuapp.com/api/search/ (?category=... / ?term=... / ?category=...&term=...)

## Authors

* **Joshua Preuß** - *Initial work* - [jpreuss00](https://github.com/jpreuss00)
