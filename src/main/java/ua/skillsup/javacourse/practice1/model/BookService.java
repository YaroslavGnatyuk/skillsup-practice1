package ua.skillsup.javacourse.practice1.model;

import java.time.LocalDate;
import java.util.List;

/**
 * @author leopold
 * @since 27/02/16
 */
public interface BookService {

  /**
   * Find author by name.
   */
  Author findAuthorByName(String name);

  /**
   * Fetch list of all authors in database.
   */
  List<Author> getAllAuthors();

  /**
   * Fetch all books written by author by his name.
   */
  List<Book> getBooksForAuthor(String authorName);

  /**
   * Fetch books of a specific author
   */
  List<Book> getBooksForAuthor(Author author);

  /**
   * Get all books, whose authors lived in a specific country
   */
  List<Book> findBooksWrittenIn(String country);

  /**
   * Fetch all authors who have at least one book published after a specified date.
   */
  List<Author> findAuthorsActiveAfter(LocalDate when);

  /**
   * Find author who wrote a book with given title
   */
  Author findAuthorByBook(String bookTitle);

  /**
   * Get basic author information: number of books, number of languages he wrote in, date of last
   * published book.
   */
  AuthorInfo getAuthorInfo(String name);
}
