package ua.skillsup.javacourse.practice1.model;

import java.time.LocalDate;
import java.util.List;

import ua.skillsup.javacourse.practice1.db.DbProps;
import ua.skillsup.javacourse.practice1.db.DbReader;

/**
 * @author leopold
 * @since 27/02/16
 */
public class BetterBookService implements BookService {

  //  private final DbProps dbProps;
  private final DbReader<Author> authorReader;

  public BetterBookService(DbProps dbProps) {
    this.authorReader = new DbReader<>(Author.class, dbProps);
  }

  @Override
  public Author findAuthorByName(String name) {
    // an example of using DbReader.
    return authorReader.readFirst("name = ?", name);
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
