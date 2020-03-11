package crawler;

import java.sql.Connection;

import crawler.domain.ArticleUsecase;
import crawler.domain.StorageUsecase;
import crawler.infrastructure.ArticleRepository;
import crawler.infrastructure.RssfeedReader;
import crawler.infrastructure.database.PostgreSQLJDBC;
import crawler.infrastructure.database.ReadDatabase;
import crawler.infrastructure.web.Webserver;

public class Main {

    public static void main(String... args) throws Exception {
        
        String database_url = System.getenv("DATABASE_URL");
        String host = "";
        String user = "";
        String password = "";
        String database = "";

        if(database_url != null && !database_url.isEmpty()){
             host = database_url.substring(91, 131);
             user = database_url.substring(11, 25);
             password = database_url.substring(26, 90);
             database = database_url.substring(137, 151);
        } else {
             host = System.getenv("DBHOST");
             user = System.getenv("DBUSER");
             password = System.getenv("DBPWD");
             database = System.getenv("DB");
        }

        if(host == null || host.isEmpty() || user == null || user.isEmpty() || password == null || password.isEmpty() || database == null || database.isEmpty()){
            System.err.println("Missing environment variables");
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

        Webserver webserver = new Webserver(connection);
        webserver.startJetty();
    }
}