package crawler.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

public class PostgreSQLJDBC {

   private String host;
   private String user;
   private String password;
   private String database;
   private Connection connection;

   public PostgreSQLJDBC(String host, String user, String password, String database) {
      this.host = host;
      this.user = user;
      this.password = password;
      this.database = database;
   }

   public boolean initDatabase() {
      try {
         //delete the "+ database" when using local on your System / only for heroku
         String url = "jdbc:postgresql://" + host + ":5432/" + database;
         connection = DriverManager.getConnection(url, user, password);
         ResultSet resultDB = connection.getMetaData().getCatalogs();
         while (resultDB.next()) {
            String catalogs = resultDB.getString(1);
            if (database.equals(catalogs)) {
               System.out.println("Database already exists...creating table");
            } else {
               System.out.println("Creating Database...creating table next");
               Statement statement = connection.createStatement();
               statement.executeUpdate("CREATE DATABASE " + database + "");
            }
         }
         connection.close();
         connection = DriverManager.getConnection(
               "jdbc:postgresql://" + host + ":5432/" + database + "?user=" + user + "&password=" + password);
         DatabaseMetaData dbmd = connection.getMetaData();
         ResultSet resultTables = dbmd.getTables(null, null, database, null);
         if (resultTables.next()) {
            System.out.println("Creating Table...starting app next");
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                  "CREATE TABLE articles (guid int, category varchar, title varchar, description varchar, pubdate timestamp)");
         } else {
            System.out.println("Table articles already exists...starting app");
         }
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
         return false;
      }
   }

   public Connection connect() {
      try {
         Class.forName("org.postgresql.Driver");
         connection = DriverManager.getConnection(
               "jdbc:postgresql://" + host + ":5432/" + database + "?user=" + user + "&password=" + password);
         return connection;
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }
      return null;
   }
}