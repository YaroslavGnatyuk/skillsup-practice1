package ua.skillsup.javacourse.practice1;

import org.h2.tools.RunScript;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Collection;

import ua.skillsup.javacourse.practice1.db.DbProps;
import ua.skillsup.javacourse.practice1.model.SimpleBookDao;

/**
 * @author leopold
 * @since 29/02/16
 */
public class PrintResults {

  public static void main(String[] args) throws SQLException {

    final DbProps dbProps = DbProps.fromProperties("conf.properties");
    RunScript.execute(dbProps.url(), dbProps.user(), dbProps.password(),
                      "classpath:sql/schema.sql", Charset.forName("UTF-8"), false);

    final SimpleBookDao bookService = new SimpleBookDao(dbProps);

    printResult(
        bookService.findBooksWrittenIn("Russia")
    );
  }

  private static void printResult(Object result) {
    if (result instanceof Collection) {
      if (((Collection) result).isEmpty()) {
        System.out.println("<EMPTY SET>");
      } else {
        ((Collection<?>) result).forEach(System.out::println);
      }
    } else {
      System.out.println(result);
    }
  }

}
