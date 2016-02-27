package ua.skillsup.javacourse.practice1.model;

import org.junit.Ignore;
import org.junit.Test;

import ua.skillsup.javacourse.practice1.db.DbProps;

/**
 * @author leopold
 * @since 27/02/16
 */
public class BetterBookServiceTest extends BookServiceTest {

  @Override
  protected BookService createBookService(DbProps dbProps) {
    return new BetterBookService(dbProps);
  }

  @Test
  @Ignore
  @Override
  public void testLoadAuthors() throws Exception {
    super.testLoadAuthors();
  }

  @Test
  @Ignore
  @Override
  public void testFindAuthorByName() throws Exception {
    super.testFindAuthorByName();
  }

  @Test
  @Ignore
  @Override
  public void testLoadBooks() throws Exception {
    super.testLoadBooks();
  }

  @Test
  @Ignore
  @Override
  public void testFindBooksWrittenIn() throws Exception {
    super.testFindBooksWrittenIn();
  }

  @Test
  @Ignore
  @Override
  public void testGetAuthorInfo() {
    super.testGetAuthorInfo();
  }

  @Test
  @Ignore
  @Override
  public void testFindAuthorsActiveAfter() {
    super.testFindAuthorsActiveAfter();
  }
}
