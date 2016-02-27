package ua.skillsup.javacourse.practice1.model;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import ua.skillsup.javacourse.practice1.db.DbProps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author leopold
 * @since 27/02/16
 */
public class SimpleBookServiceTest {

  private static DbProps dbProps;
  private BookService bookService;

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

    bookService = new SimpleBookService(dbProps);
  }

  @After
  public void after() throws SQLException {
    RunScript.execute(
        dbProps.url(), dbProps.user(), dbProps.password(),
        "classpath:sql/h2_shutdown.sql", Charset.forName("UTF-8"), false
    );
  }

  @Test
  public void testLoadAuthors() throws Exception {

    final List<Author> allAuthors = bookService.getAllAuthors();
    assertEquals(7, allAuthors.size());

    final Author steinbeck = allAuthors.stream()
        .filter(a -> a.getName().contains("Steinbeck"))
        .findAny()
        .orElse(null);

    assertNotNull(steinbeck);

    assertEquals("John Steinbeck", steinbeck.getName());
    assertEquals(LocalDate.parse("1902-02-27"), steinbeck.getBirthday());
    assertEquals(LocalDate.parse("1968-12-20"), steinbeck.getDeath());
    assertEquals("USA", steinbeck.getCountry());
    assertTrue(steinbeck.getNotes().contains("John Ernst Steinbeck, Jr."));
  }

  @Test
  public void testFindAuthorByName() throws Exception {
    final Author herbert = bookService.findAuthor("Herbert");

    assertNotNull(herbert);
    assertEquals("Frank Herbert", herbert.getName());
  }

  @Test
  public void testLoadBooks() throws Exception {

    final Author steinbeck = bookService.findAuthor("Steinbeck");

    final List<Book> booksByAuthor = bookService.getBooksForAuthor(steinbeck);
    final List<Book> booksByAuthorName = bookService.getBooksForAuthor("Steinbeck");

    assertEquals(booksByAuthor, booksByAuthorName);
    assertEquals(2, booksByAuthor.size());

    final Book theGrapesOfWrath = booksByAuthor.stream()
        .filter(b -> b.getTitle().equals("The Grapes of Wrath"))
        .findAny()
        .orElse(null);

    assertNotNull(theGrapesOfWrath);

    assertEquals("English", theGrapesOfWrath.getOriginalLanguage());
    assertNull(theGrapesOfWrath.getIsbn());
    assertEquals(LocalDate.parse("1939-04-14"), theGrapesOfWrath.getPublished());
    assertTrue(theGrapesOfWrath.getSummary().contains("The Grapes of Wrath"));
  }

  @Test
  public void testFindBooksWrittenIn() throws Exception {
    final List<Book> ukBooks = bookService.findBooksWrittenIn("UK");

    assertEquals(3, ukBooks.size());

    assertTrue(ukBooks.stream().anyMatch(b -> b.getTitle().equals("Nineteen Eighty-Four")));
    assertTrue(ukBooks.stream().anyMatch(b -> b.getTitle().equals("Animal Farm")));
    assertTrue(ukBooks.stream().anyMatch(b -> b.getTitle().equals("The Hobbit")));

  }

  @Test
  public void testGetAuthorInfo() {
    final AuthorInfo nabokovInfo = bookService.getAuthorInfo("Nabokov");

    assertNotNull(nabokovInfo);
    assertEquals("Vladimir Nabokov", nabokovInfo.getAuthor().getName());

    assertEquals(3, nabokovInfo.getBooksCount());
    assertEquals(2, nabokovInfo.getLangCount());
    assertEquals(LocalDate.parse("1957-01-01"), nabokovInfo.getLastBook());
  }
}