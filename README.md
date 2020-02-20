# Crawler

Displays the Welt RSS Feed categorized and saved in a postgres Database.

## Built With

* [Java]

## How to use

1: Start a postgres Docker Container:
docker pull postgres
docker run -p 5432:5432 -e POSTGRES_PASSWORD="choose a password" -d postgres

2: Start your application:
docker run --env DBHOST="IP adress of your Docker Container" --env DBUSER=postgres --env DBPWD="your choosen password" -d "Image name"

3: Connect to your Postgres Database:
psql -h localhost -U postgres -d postgres
OR
open it in your webbrowser with PG Admin 4 or your IDE

4: Create the Database and the table:
CREATE TABLE articles (guid int, category varchar, title varchar, description varchar, pubdate timestamp)

## Authors

* **Joshua Preuß** - *Initial work* - [jpreuss00](https://github.com/jpreuss00)
