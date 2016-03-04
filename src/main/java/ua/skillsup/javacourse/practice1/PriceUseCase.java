package ua.skillsup.javacourse.practice1;

import org.h2.tools.RunScript;

import java.nio.charset.Charset;
import java.sql.SQLException;

import ua.skillsup.javacourse.practice1.db.DbProps;
import ua.skillsup.javacourse.practice1.model.SimpleBookDao;
import ua.skillsup.javacourse.practice1.prices.BookPriceService;
import ua.skillsup.javacourse.practice1.prices.PriceProviderImpl;

/**
 * @author leopold
 * @since 4/03/16
 */
public class PriceUseCase {

  public static void main(String[] args) throws SQLException {
    final DbProps dbProps = DbProps.fromProperties("conf.properties");
    RunScript.execute(dbProps.url(), dbProps.user(), dbProps.password(),
                      "classpath:sql/schema.sql", Charset.forName("UTF-8"), false);

    final SimpleBookDao bookDao = new SimpleBookDao(dbProps);

    final BookPriceService bookPriceService = new BookPriceService(bookDao,
                                                                   new PriceProviderImpl(),
                                                                   new PriceProviderImpl()
    );

    System.out.println(
        bookPriceService.getBestPrice("Orwell", "Animal Farm")
    );

  }

}
