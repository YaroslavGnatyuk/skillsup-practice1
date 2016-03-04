package ua.skillsup.javacourse.practice1.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ua.skillsup.javacourse.practice1.db.DbException;
import ua.skillsup.javacourse.practice1.db.DbProps;

import static java.util.Collections.unmodifiableList;
import static ua.skillsup.javacourse.practice1.db.DbUtils.likeParam;
import static ua.skillsup.javacourse.practice1.db.DbUtils.sqlToLocalDate;

/**
 * @author leopold
 * @since 27/02/16
 */
@SuppressWarnings("SqlResolve")
public class SimpleBookDao implements BookDao {

  private final DbProps dbProps;

  public SimpleBookDao(DbProps dbProps) {
    this.dbProps = dbProps;
  }

  private Connection newConnection() throws SQLException {
    return DriverManager.getConnection(dbProps.url(), dbProps.user(), dbProps.password());
  }

  private static final String AUTHORS_SELECT_ALL =
      "SELECT id, name, birthday, death, country, notes FROM authors";

  @Override
  public List<Author> getAllAuthors() {
    try (final Connection conn = newConnection();
         final Statement st = conn.createStatement()) {

      final ResultSet resultSet = st.executeQuery(AUTHORS_SELECT_ALL);

      final List<Author> authors = new ArrayList<>();
      while (resultSet.next()) {

        final Author author = new Author();
        author.setId(resultSet.getInt(1));
        author.setName(resultSet.getString(2));
        author.setBirthday(resultSet.getDate(3).toLocalDate());
        author.setDeath(sqlToLocalDate(resultSet.getDate(4)));
        author.setCountry(resultSet.getString(5));
        author.setNotes(resultSet.getString(6));

        authors.add(author);
      }

      return unmodifiableList(authors);

    } catch (SQLException e) {
      throw new DbException("Db read error", e);
    }
  }

  private static final String AUTHOR_FIND_BY_NAME =
      "SELECT id, name, birthday, death, country, notes FROM authors " +
      "WHERE name LIKE ?";

  @Override
  public Author findAuthorByName(String name) {
    try (final Connection conn = newConnection();
         final PreparedStatement pst = conn.prepareStatement(AUTHOR_FIND_BY_NAME)) {

      pst.setString(1, '%' + name + '%');
      final ResultSet resultSet = pst.executeQuery();

      return resultSet.next() ? readAuthor(resultSet) : null;

    } catch (SQLException e) {
      throw new DbException("Db read error", e);
    }
  }

  private static final String BOOK_FIND_BY_AUTHOR =
      "SELECT id, "
      + "title, "
      + "author_id, "
      + "original_language, "
      + "isbn, "
      + "published, "
      + "abstract, "
      + "avg_rating, "
      + "reviews_count " +
      "FROM books WHERE author_id = ?";

  @Override
  public List<Book> getBooksForAuthor(Author author) {
    try (final Connection conn = newConnection();
         final PreparedStatement pst = conn.prepareStatement(BOOK_FIND_BY_AUTHOR)) {

      pst.setInt(1, author.getId());

      final ResultSet resultSet = pst.executeQuery();
      final List<Book> books = new ArrayList<>();

      while (resultSet.next()) {
        books.add(readBook(resultSet));
      }

      return unmodifiableList(books);
    } catch (SQLException e) {
      throw new DbException("Db read error", e);
    }
  }

  private static final String BOOK_FIND_BY_AUTHOR_NAME =
      "SELECT id, "
      + "title, "
      + "author_id, "
      + "original_language, "
      + "isbn, "
      + "published, "
      + "abstract, "
      + "avg_rating, "
      + "reviews_count " +
      "FROM books WHERE author_id IN ("
      + "SELECT id FROM authors WHERE name LIKE ?" +
      ")";

  @Override
  public List<Book> getBooksForAuthor(String authorName) {
    try (final Connection conn = newConnection();
         final PreparedStatement pst = conn.prepareStatement(BOOK_FIND_BY_AUTHOR_NAME)) {

      pst.setObject(1, likeParam(authorName));

      final ResultSet resultSet = pst.executeQuery();
      final List<Book> books = new ArrayList<>();

      while (resultSet.next()) {
        books.add(readBook(resultSet));
      }

      return unmodifiableList(books);
    } catch (SQLException e) {
      throw new DbException("Db read error", e);
    }
  }

  private static final String BOOK_FIND_WRITTEN_IN_COUNTRY =
      "SELECT b.id id, "
      + "b.title title, "
      + "b.author_id author_id, "
      + "b.original_language original_language, "
      + "b.isbn isbn, "
      + "b.published published, "
      + "b.abstract abstract, "
      + "b.avg_rating avg_rating, "
      + "b.reviews_count reviews_count " +
      "FROM books b LEFT JOIN authors a ON b.author_id = a.id " +
      "WHERE a.country = ?";

  @Override
  public List<Book> findBooksWrittenIn(String country) {
    try (final Connection conn = newConnection();
         final PreparedStatement pst = conn.prepareStatement(BOOK_FIND_WRITTEN_IN_COUNTRY)) {

      pst.setString(1, country);

      final ResultSet resultSet = pst.executeQuery();
      final List<Book> books = new ArrayList<>();

      while (resultSet.next()) {
        books.add(readBook(resultSet));
      }

      return unmodifiableList(books);
    } catch (SQLException e) {
      throw new DbException("Db read error", e);
    }
  }

  private static final String AUTHOR_GET_INFO =
      "SELECT a.*, "
      + "count(b.id) bookCount, "
      + "count(DISTINCT b.original_language) langCount, "
      + "max(b.published) lastBook " +
      "FROM authors a LEFT JOIN books b ON a.id = b.author_id " +
      "WHERE a.name LIKE ?" +
      "GROUP BY (a.id)";

  @Override
  public AuthorInfo getAuthorInfo(String name) {
    try (final Connection conn = newConnection();
         final PreparedStatement pst = conn.prepareStatement(AUTHOR_GET_INFO)) {

      pst.setString(1, '%' + name + '%');

      final ResultSet resultSet = pst.executeQuery();
      return resultSet.next() ? new AuthorInfo(
          readAuthor(resultSet),
          resultSet.getInt("bookCount"),
          sqlToLocalDate(resultSet.getDate("lastBook")),
          resultSet.getInt("langCount")
      ) : null;

    } catch (SQLException e) {
      throw new DbException("Db read error", e);
    }
  }

  private static final String AUTHORS_FIND_ACTIVE_AFTER =
      "SELECT a.* " +
      "FROM authors a RIGHT JOIN books b ON a.id = b.author_id " +
      "GROUP BY (a.id) " +
      "HAVING max(b.published) >= ?";

  @Override
  public List<Author> findAuthorsActiveAfter(LocalDate when) {
    try (final Connection conn = newConnection();
         final PreparedStatement pst = conn.prepareStatement(AUTHORS_FIND_ACTIVE_AFTER)) {

      pst.setDate(1, Date.valueOf(when));
      final ResultSet resultSet = pst.executeQuery();

      final List<Author> authors = new ArrayList<>();
      while (resultSet.next()) {
        authors.add(readAuthor(resultSet));
      }

      return unmodifiableList(authors);

    } catch (SQLException e) {
      throw new DbException("Db read error", e);
    }
  }

  private static final String AUTHOR_FIND_BY_BOOK =
      "SELECT a.* " +
      "FROM authors a RIGHT JOIN books b ON a.id = b.author_id " +
      "WHERE b.title LIKE ?";

  @Override
  public Author findAuthorByBook(String bookTitle) {
    try (final Connection conn = newConnection();
         final PreparedStatement pst = conn.prepareStatement(AUTHOR_FIND_BY_BOOK)) {

      pst.setObject(1, '%' + bookTitle + '%');
      final ResultSet resultSet = pst.executeQuery();

      return resultSet.next() ? readAuthor(resultSet) : null;

    } catch (SQLException e) {
      throw new DbException("Db read error", e);
    }
  }

  private static Book readBook(ResultSet resultSet) throws SQLException {
    final Book book = new Book();
    book.setId(resultSet.getInt("id"));
    book.setTitle(resultSet.getString("title"));
    book.setAuthorId(resultSet.getInt("author_id"));
    book.setOriginalLanguage(resultSet.getString("original_language"));
    book.setIsbn(resultSet.getString("isbn"));
    book.setPublished(resultSet.getDate("published").toLocalDate());
    book.setSummary(resultSet.getString("abstract"));
    book.setAvgRating(resultSet.getFloat("avg_rating"));
    book.setReviewsCount(resultSet.getInt("reviews_count"));

    return book;
  }

  private static Author readAuthor(ResultSet resultSet) throws SQLException {
    final Author author = new Author();
    author.setId(resultSet.getInt("id"));
    author.setName(resultSet.getString("name"));
    author.setBirthday(resultSet.getDate("birthday").toLocalDate());
    author.setDeath(sqlToLocalDate(resultSet.getDate("death")));
    author.setCountry(resultSet.getString("country"));
    author.setNotes(resultSet.getString("notes"));

    return author;
  }
}
