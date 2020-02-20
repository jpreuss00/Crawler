package src.main.java.crawler;

import java.sql.Connection;

import src.main.java.crawler.domain.ArticleUsecase;
import src.main.java.crawler.domain.StorageUsecase;
import src.main.java.crawler.infrastructure.ArticleRepository;
import src.main.java.crawler.infrastructure.RssfeedReader;

public class Crawler {

    public static void main(String... args) {

        String host = System.getenv("DBHOST");
        String user = System.getenv("DBUSER");
        String password = System.getenv("DBPWD");
        String database = System.getenv("DB");

        RssfeedReader rssReader = new RssfeedReader();
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        Connection connection = postgreSQLJDBC.connection(host, user, password, database);
        ArticleRepository articleRepository = new ArticleRepository(connection);
        ArticleUsecase articleUsecase = new ArticleUsecase(rssReader);
        ReadDatabase readDatabase = new ReadDatabase(connection);
        StorageUsecase storageUsecase = new StorageUsecase(articleUsecase, articleRepository, readDatabase);

        ExecuteTimer executeTimer = new ExecuteTimer();
        executeTimer.timing(storageUsecase);
    }
}