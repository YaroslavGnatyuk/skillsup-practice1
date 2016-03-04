package ua.skillsup.javacourse.practice1.model;

import java.time.LocalDate;
import java.util.List;

import ua.skillsup.javacourse.practice1.db.DbProps;
import ua.skillsup.javacourse.practice1.db.DbReader;

import static ua.skillsup.javacourse.practice1.db.DbUtils.likeParam;

/**
 * @author leopold
 * @since 27/02/16
 */
public class BetterBookService implements BookDao {

  //  private final DbProps dbProps;
  private final DbReader<Author> authorReader;
  private final DbReader<Book> bookDbReader;

  public BetterBookService(DbProps dbProps) {
    this.authorReader = new DbReader<>(Author.class, dbProps);
    this.bookDbReader = new DbReader<>(Book.class, dbProps);
  }

  @Override
  public Author findAuthorByName(String name) {
    return authorReader.readFirst("name LIKE ?", '%' + name + '%');
  }

  @Override
  public List<Author> getAllAuthors() {
    return authorReader.readAll();
  }

  @Override
  public List<Book> getBooksForAuthor(Author author) {
    return bookDbReader.readList("author_id = ?", author.getId());
  }

  @Override
  public List<Book> getBooksForAuthor(String authorName) {
    return bookDbReader.readList("author_id IN (SELECT id FROM authors WHERE name LIKE ?)",
                                 likeParam(authorName));
  }

  @Override
  public List<Book> findBooksWrittenIn(String country) {
    return bookDbReader
        .readList("author_id IN (SELECT id FROM authors WHERE country = ?)", country);
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
