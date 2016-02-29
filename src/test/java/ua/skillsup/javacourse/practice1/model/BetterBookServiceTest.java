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
  public void testGetAuthorInfo() {
    super.testGetAuthorInfo();
  }

  @Test
  @Ignore
  @Override
  public void testFindAuthorsActiveAfter() {
    super.testFindAuthorsActiveAfter();
  }

  @Test
  @Ignore
  @Override
  public void findAuthorByBook() {
    super.findAuthorByBook();
  }
}
