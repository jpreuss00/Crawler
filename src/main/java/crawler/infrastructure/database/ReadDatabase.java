package crawler.infrastructure.database;

import crawler.domain.Article;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadDatabase {

    private final Connection connection;

    public ReadDatabase(Connection connection) {
        this.connection = connection;
    }

    public String dataReader() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT title FROM articles WHERE pubdate <= CURRENT_TIMESTAMP ORDER BY pubdate DESC LIMIT 1");
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String resultString = result.getString(1);
                System.out.println("Der neueste Beitrag handelt von: '" + resultString + "'");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return "";
    }

    public List<Article> articleReader(String categorySearch, String termSearch, int limit) {
        try {
            List<Article> articles = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE category LIKE ? AND pubdate <= CURRENT_TIMESTAMP AND (title LIKE ? OR description LIKE ?) ORDER BY pubdate DESC LIMIT ?");
            preparedStatement.setString(1, "%" + categorySearch + "%");
            preparedStatement.setString(2, "%" + termSearch + "%");
            preparedStatement.setString(3, "%" + termSearch + "%");
            preparedStatement.setInt(4, limit);
            System.out.println("Search: " + categorySearch + ", " + termSearch);
            System.out.println(preparedStatement);
            ResultSet result = preparedStatement.executeQuery();
            for (int i = 0; i < limit; i++) {
                if (result.next()) {
                    int guid = 0;
                    String category = "";
                    String title = "";
                    String description = "";
                    String pubDate = "";
                    String link = "";
                    for (int j = 1; j < 7; j++) {
                        String resultString = result.getString(j);

                        switch (j) {
                            case 1:
                                guid = Integer.parseInt(resultString);
                                break;
                            case 2:
                                category = resultString;
                                break;
                            case 3:
                                title = resultString;
                                break;
                            case 4:
                                description = resultString;
                                break;
                            case 5:
                                pubDate = resultString;
                                break;
                            case 6:
                                link = resultString;
                                break;
                        }
                    }
                    Article article = new Article(title, category, guid, pubDate, description, link);
                    articles.add(article);
                } else {
                    break;
                }
            }
            return articles;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }
}