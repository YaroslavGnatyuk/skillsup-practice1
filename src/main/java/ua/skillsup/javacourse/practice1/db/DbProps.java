package ua.skillsup.javacourse.practice1.db;

import java.io.IOException;
import java.util.Properties;

/**
 * @author leopold
 * @since 27/02/16
 */
public class DbProps {

  private final String url;
  private final String user;
  private final String password;

  private DbProps(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public static DbProps fromProperties(String fileName) {
    final Properties properties = new Properties();
    try {
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
    } catch (IOException e) {
      throw new DbException("Error reading DB properties", e);
    }

    final String url = properties.getProperty("db.url");
    if (url == null || url.isEmpty()) {
      throw new DbException("DB url not set");
    }

    final String jdbcDriver = properties.getProperty("db.jdbcDriver");
    if (jdbcDriver == null || jdbcDriver.isEmpty()) {
      throw new DbException("JDBC driver class not set");
    }

    try {
      Class.forName(jdbcDriver);
    } catch (ClassNotFoundException e) {
      throw new DbException("JDBC Driver not found in classpath", e);
    }

    return new DbProps(
        url,
        properties.getProperty("db.user"),
        properties.getProperty("db.password")
    );
  }

  public String url() {
    return url;
  }

  public String user() {
    return user;
  }

  public String password() {
    return password;
  }

  @Override
  public String toString() {
    return "DbProps{" +
           "url='" + url + '\'' +
           '}';
  }
}
