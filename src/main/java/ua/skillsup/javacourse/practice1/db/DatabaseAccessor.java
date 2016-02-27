package ua.skillsup.javacourse.practice1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author leopold
 * @since 27/02/16
 */
public class DatabaseAccessor {

  private final String url;
  private final String username;
  private final String password;


  public DatabaseAccessor(Properties properties) {
    this.url = properties.getProperty("db.url");
    this.username = properties.getProperty("db.user");
    this.password = properties.getProperty("db.password");
  }

  public <T> List<T> findAll(Class<T> clazz) {
    return null;
  }

  private ResultSet query(String sql, Object... params) throws SQLException {
    try (final Connection connection = DriverManager.getConnection(url, username, password);
         final PreparedStatement statement = connection.prepareStatement(sql)) {

      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          statement.setObject(i + 1, params[i]);
        }
      }

      return statement.executeQuery();
    }
  }
}
