package ua.skillsup.javacourse.practice1.model;

import org.junit.Ignore;
import org.junit.Test;

import ua.skillsup.javacourse.practice1.db.DbProps;

/**
 * @author leopold
 * @since 27/02/16
 */
public class SimpleBookServiceTest extends BookServiceTest {

  @Override
  protected BookService createBookService(DbProps dbProps) {
    return new SimpleBookService(dbProps);
  }

  @Test
  @Ignore
  @Override
  public void findAuthorByBook() {
    super.findAuthorByBook();
  }
}
