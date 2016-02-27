package ua.skillsup.javacourse.practice1.db;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @author leopold
 * @since 27/02/16
 */
public final class DbUtils {

  private DbUtils() {
  }

  public static LocalDate sqlToLocalDate(Date sqlDate) {
    return sqlDate == null ? null : sqlDate.toLocalDate();
  }
}
