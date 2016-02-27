package ua.skillsup.javacourse.practice1.model;

import java.util.List;

/**
 * @author leopold
 * @since 27/02/16
 */
public interface BookService {

  Author findAuthor(String name);

  List<Author> getAllAuthors();

  List<Book> getBooksForAuthor(String authorName);

  List<Book> getBooksForAuthor(Author author);

  List<Book> findBooksWrittenIn(String country);

  AuthorInfo getAuthorInfo(String name);
}
