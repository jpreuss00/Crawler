package crawler.infrastructure;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import crawler.infrastructure.database.ReadDatabase;
import crawler.domain.*;

public class ArticleRepository {

    private final Connection connection;

    public ArticleRepository(Connection c) {
        this.connection = c;
    }

    public void checkArticleOverflow() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM articles");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) == 9999) {
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM articles WHERE guid = (SELECT guid FROM articles ORDER BY pubdate ASC LIMIT 1)");
                    deleteStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void save(Article article, ReadDatabase readDatabase) {
        if (compareGuids(article)) {
            checkArticleOverflow();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO articles (Guid, Category, Title, pubDate, description, link)" + "VALUES (?, ?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, article.getGuid());
                preparedStatement.setString(2, article.getCategory());
                preparedStatement.setString(3, article.getTitle());
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                Date parsedDate = dateFormat.parse(article.getPubDate());
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                preparedStatement.setTimestamp(4, timestamp);
                preparedStatement.setString(5, article.getDescription());
                preparedStatement.setString(6, article.getLink());
                preparedStatement.executeUpdate();
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
            return !resultGuid.isBeforeFirst();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }
}