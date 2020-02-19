package src.main.java.crawler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
            result.next();
            String resultString = result.getString(1);
            System.out.println("Der neueste Beitrag handelt von: '" + resultString + "'");
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return "";
    }
}