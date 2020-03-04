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

    public List<Article> articleReader(String categorySearch, String termSearch){
        try {
            List<Article> articles = new ArrayList<>();
            Statement stmt = connection.createStatement();
            String sql = "";
            if(categorySearch.isEmpty() && termSearch.isEmpty()){
                sql = "SELECT * FROM articles WHERE pubdate <= CURRENT_TIMESTAMP ORDER BY pubdate DESC LIMIT 10";
                System.out.println("normalSearch: " + categorySearch + ", " + termSearch);
            } else if(!categorySearch.isEmpty() && termSearch.isEmpty()){
                sql = "SELECT * FROM articles WHERE category = '" + categorySearch + "' AND pubdate <= CURRENT_TIMESTAMP ORDER BY pubdate DESC LIMIT 10";
                System.out.println("categorySearch: " + categorySearch + ", " + termSearch);
            } else if (categorySearch.isEmpty() && !termSearch.isEmpty()){
                sql = "SELECT * FROM articles WHERE pubdate <= CURRENT_TIMESTAMP AND title LIKE '%" + termSearch + "%' OR description LIKE '%" + termSearch + "%' ORDER BY pubdate DESC LIMIT 10";
                System.out.println("termSearch: " + categorySearch + ", " + termSearch);
            } else if (!categorySearch.isEmpty() && !termSearch.isEmpty()){
                sql = "SELECT * FROM articles WHERE category = '" + categorySearch + "' AND pubdate <= CURRENT_TIMESTAMP AND (title LIKE '%" + termSearch + "%' OR description LIKE '%" + termSearch + "%') ORDER BY pubdate DESC LIMIT 10";
                System.out.println("bothSearch: " + categorySearch + ", " + termSearch);
                System.out.println(sql);
            }
            ResultSet result = stmt.executeQuery(sql);

            for(int i = 0; i < 10; i++){
                if(result.next()){
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