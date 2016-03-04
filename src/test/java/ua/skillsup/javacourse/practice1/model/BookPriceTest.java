package ua.skillsup.javacourse.practice1.model;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;

import ua.skillsup.javacourse.practice1.db.DbProps;
import ua.skillsup.javacourse.practice1.prices.BookPriceService;
import ua.skillsup.javacourse.practice1.prices.PriceProvider;
import ua.skillsup.javacourse.practice1.prices.PriceProviderImpl;

/**
 * @author leopold
 * @since 4/03/16
 */
public class BookPriceTest {

  private static DbProps dbProps;
  private BookDao bookDao;
//
//  protected abstract BookService createBookService(DbProps dbProps);

  @BeforeClass
  public static void initProps() throws IOException {
    dbProps = DbProps.fromProperties("conf.properties");
  }

  @Before
  public void before() throws SQLException {
    RunScript.execute(
        dbProps.url(), dbProps.user(), dbProps.password(),
        "classpath:sql/schema.sql", Charset.forName("UTF-8"), false
    );

    bookDao = new SimpleBookDao(dbProps);
  }

  @After
  public void after() throws SQLException {
    RunScript.execute(
        dbProps.url(), dbProps.user(), dbProps.password(),
        "classpath:sql/h2_shutdown.sql", Charset.forName("UTF-8"), false
    );
  }

  @Test
  public void testBookPrice() throws Exception {

    final PriceProvider priceProvider1 = Mockito.mock(PriceProvider.class);
    final PriceProvider priceProvider2 = Mockito.mock(PriceProvider.class);

    final ArgumentCaptor<PriceProvider.BookInfo> bookInfo =
        ArgumentCaptor.forClass(PriceProvider.BookInfo.class);

    Mockito.when(priceProvider1.getPrice(bookInfo.capture()))
        .thenReturn(5.0);

    Mockito.when(priceProvider2.getPrice(Mockito.any()))
        .thenReturn(5.7);

    final BookPriceService bookPriceService = new BookPriceService(
        bookDao,
        priceProvider1,
        priceProvider2
    );

    final Double price = bookPriceService.getBestPrice("Orwell", "Animal Farm");

    Assert.assertNotNull(price);
    Assert.assertEquals(5.0, price, 0.0);
    Assert.assertEquals("978-0-452-28424-1", bookInfo.getValue().getIsbn());

    Mockito.verify(priceProvider1).getPrice(Mockito.any());

    Mockito.verifyNoMoreInteractions(priceProvider1);
  }

  @Test
  public void testWithSpy() throws Exception {
    final PriceProvider priceProvider1 = Mockito.mock(PriceProvider.class);
    final PriceProvider priceProvider2 = Mockito.mock(PriceProvider.class);

    Mockito.when(priceProvider1.getPrice(Mockito.any()))
        .thenReturn(5.0);

    Mockito.when(priceProvider2.getPrice(Mockito.any()))
        .thenReturn(5.7);

    final BookDao bookDaoSpy = Mockito.spy(bookDao);

    final BookPriceService bookPriceService = new BookPriceService(
        bookDaoSpy,
        priceProvider1,
        priceProvider2
    );

    final Double price = bookPriceService.getBestPrice("Orwell", "Animal Farm");

    Assert.assertNotNull(price);
    Assert.assertEquals(5.0, price, 0.0);

    Mockito.verify(bookDaoSpy).getBooksForAuthor(Mockito.eq("Orwell"));

    Mockito.verify(priceProvider1).getPrice(Mockito.any());

    Mockito.verifyNoMoreInteractions(priceProvider1, bookDaoSpy);
  }
}
