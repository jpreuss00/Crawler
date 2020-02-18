package src.main.java.crawler;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLJDBC {
   public Connection connection(String host, String user, String password, String database) {
      Connection c = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://" + host + ":5432/" + database + "", "" + user + "", "" + password + "");
        return c;
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }
      return null;
   }
}