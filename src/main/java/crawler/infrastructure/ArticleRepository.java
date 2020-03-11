package crawler.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import crawler.infrastructure.database.ReadDatabase;
import crawler.domain.*;

public class ArticleRepository {

    private final Connection connection;

    public ArticleRepository(Connection c) {
        this.connection = c;
    }

    public boolean checkArticleOverflow(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM articles");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getInt(1) == 99999){
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM articles WHERE guid = (SELECT guid FROM articles ORDER BY pubdate ASC LIMIT 1)");
                    deleteStatement.executeUpdate();
                }
            }
            preparedStatement.close();
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    public void save(Article article, ReadDatabase readDatabase) {
            if (compareGuids(article)) {
                checkArticleOverflow();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO articles (Guid, Category, Title, pubdate, description, link)" + "VALUES (?, ?, ?, ?, ?, ?)");
                    preparedStatement.setInt(1, article.getGuid());
                    preparedStatement.setString(2, article.getCategory());
                    preparedStatement.setString(3, article.getTitle());
                    preparedStatement.setString(4, article.getPubDate());
                    preparedStatement.setString(5, article.getDescription());
                    preparedStatement.setString(6, article.getLink());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    readDatabase.dataReader();
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
            }
    }

    public boolean compareGuids(Article article) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT guid FROM articles WHERE guid = ? ");
            preparedStatement.setInt(1, article.getGuid());
            ResultSet resultGuid = preparedStatement.executeQuery();
            preparedStatement.close();
            return !resultGuid.isBeforeFirst();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }
}