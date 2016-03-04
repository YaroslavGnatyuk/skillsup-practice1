package ua.skillsup.javacourse.practice1.model;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import ua.skillsup.javacourse.practice1.db.DbProps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author leopold
 * @since 4/03/16
 */
@RunWith(Parameterized.class)
public class AuthorByNameTest {

  private static DbProps dbProps;
  private BookDao bookDao;
//
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

  @Parameterized.Parameters
  public static Object[][] parameters() {
    return new Object[][]{
        new Object[]{"Herbert", "Frank Herbert"},
        new Object[]{"Orwell", "George Orwell"},
        new Object[]{"Tolkien", "John Ronald Reuel Tolkien"}
    };
  }

  @Parameterized.Parameter(0)
  public String searchString;

  @Parameterized.Parameter(1)
  public String exactName;

  @Test
  public void testFindAuthorByName() throws Exception {
    final Author herbert = bookDao.findAuthorByName(searchString);

    assertNotNull(herbert);
    assertEquals(exactName, herbert.getName());
  }

  @Test
  public void testFindAuthorsActiveAfter() {
    final List<Author> authors =
        bookDao.findAuthorsActiveAfter(LocalDate.parse("1950-01-01"));

    assertEquals(2, authors.size());

    assertTrue(authors.stream().anyMatch(a -> a.getName().equals("Frank Herbert")));
    assertTrue(authors.stream().anyMatch(a -> a.getName().equals("Vladimir Nabokov")));
  }
}
