package ua.skillsup.javacourse.practice1.model;

import java.time.LocalDate;
import java.util.List;

import ua.skillsup.javacourse.practice1.db.DbProps;

/**
 * @author leopold
 * @since 27/02/16
 */
public class BetterBookService implements BookService {

  private final DbProps dbProps;

  public BetterBookService(DbProps dbProps) {
    this.dbProps = dbProps;
  }

  @Override
  public Author findAuthorByName(String name) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public List<Author> getAllAuthors() {
    throw new UnsupportedOperationException("");
  }

  @Override
  public List<Book> getBooksForAuthor(Author author) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public List<Book> getBooksForAuthor(String authorName) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public List<Book> findBooksWrittenIn(String country) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public AuthorInfo getAuthorInfo(String name) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public List<Author> findAuthorsActiveAfter(LocalDate when) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public Author findAuthorByBook(String bookTitle) {
    throw new UnsupportedOperationException("");
  }
}
