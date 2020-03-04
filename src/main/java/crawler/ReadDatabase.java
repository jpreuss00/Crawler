package crawler;

import crawler.domain.Article;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReadDatabase {

    private final Connection connection;

    public ReadDatabase(Connection connection) {
        this.connection = connection;
    }

    public String dataReader() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT title FROM articles WHERE pubdate <= CURRENT_TIMESTAMP ORDER BY pubdate DESC LIMIT 1";
            ResultSet result = stmt.executeQuery(sql);
            if (result.next() == true) {
                String resultString = result.getString(1);
                System.out.println("Der neueste Beitrag handelt von: '" + resultString + "'");
            }
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return "";
    }

    public List<Article> articleReader(){
        try {
            List<Article> articles = new ArrayList<>();
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM articles WHERE pubdate <= CURRENT_TIMESTAMP ORDER BY pubdate DESC LIMIT 10";
            ResultSet result = stmt.executeQuery(sql);
            for(int i = 0; i < 10; i++){
                result.next();
                int guid = 0;
                String category = "";
                String title = "";
                String description = "";
                String pubDate = "";
                for(int j = 1; j < 6; j++){
                    String resultString = result.getString(j);

                    switch (j){
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
                    }
                }
                Article article = new Article(title, category, guid, pubDate, description);
                articles.add(article);
            }
            return articles;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }
}