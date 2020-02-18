package src.main.java.crawler.infrastructure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import src.main.java.crawler.domain.*;

public class ArticleRepository {

    private final Connection connection;

    public ArticleRepository(Connection c) {
        this.connection = c;
    }

    public void save(Article article) {
        if (compareGuids(article)) {
            try {
                Statement stmt = connection.createStatement();
                String sql = "INSERT INTO articles (Guid, Category, Title, pubdate, description)" + "VALUES ("
                        + article.getGuid() + ", '" + article.getCategory() + "', '" + article.getTitle() + "', '"
                        + article.getPubDate() + "', '" + article.getDescription() + "')";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
    }

    public boolean compareGuids(Article article) {
        try {
            Statement stmt = connection.createStatement();
            String guidSorter = "SELECT guid FROM articles WHERE guid = " + article.getGuid() + "";
            ResultSet resultGuid = stmt.executeQuery(guidSorter);
            if(!resultGuid.isBeforeFirst()){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }
}