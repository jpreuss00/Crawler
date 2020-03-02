package src.main.java.crawler;

import java.sql.Connection;

import src.main.java.crawler.domain.ArticleUsecase;
import src.main.java.crawler.domain.StorageUsecase;
import src.main.java.crawler.infrastructure.ArticleRepository;
import src.main.java.crawler.infrastructure.RssfeedReader;

public class Crawler {

    public static void main(String... args) {
        
        String database_url = System.getenv("DATABASE_URL");

        String host = database_url.substring(91, 131);
        String user = database_url.substring(11, 25);
        String password = database_url.substring(26, 90);
        String database = database_url.substring(137, 151);
        /*
        String host = System.getenv("DBHOST");
        String user = System.getenv("DBUSER");
        String password = System.getenv("DBPWD");
        String database = System.getenv("DB");
        */
        if(host == null || host.isEmpty() || user == null || user.isEmpty() || password == null || password.isEmpty() || database == null || database.isEmpty()){
            System.err.println("Missing envrionment variables");
            System.exit(1);
        }
        System.out.printf("Starting app with host: %s, user: %s, database: %s \n",host,user,database);
        RssfeedReader rssReader = new RssfeedReader();
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC(host, user, password, database);
        postgreSQLJDBC.initDatabase();
        Connection connection = postgreSQLJDBC.connect();
        ArticleRepository articleRepository = new ArticleRepository(connection);
        ArticleUsecase articleUsecase = new ArticleUsecase(rssReader);
        ReadDatabase readDatabase = new ReadDatabase(connection);
        StorageUsecase storageUsecase = new StorageUsecase(articleUsecase, articleRepository, readDatabase);

        ExecuteTimer executeTimer = new ExecuteTimer();
        executeTimer.timing(storageUsecase);
    }
}