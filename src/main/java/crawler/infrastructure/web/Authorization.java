package crawler.infrastructure.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Authorization {

    public boolean authorize(Connection connection, String keyPhrase) {
        if (keyPhrase == null) {
            return false;
        } else if (keyPhrase.isEmpty()) {
            return false;
        } else
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM userKeys WHERE keyphrase = ? ");
                preparedStatement.setString(1, keyPhrase);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getInt(1) != 0;
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        return false;
    }
}
