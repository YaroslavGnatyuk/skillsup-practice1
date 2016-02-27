package ua.skillsup.javacourse.practice1.model;

import java.time.LocalDate;

/**
 * @author leopold
 * @since 27/02/16
 */
public class AuthorInfo {

  private final Author author;
  private final int booksCount;
  private final LocalDate lastBook;
  private final int langCount;

  public AuthorInfo(Author author, int booksCount, LocalDate lastBook, int langCount) {
    this.author = author;
    this.booksCount = booksCount;
    this.lastBook = lastBook;

    this.langCount = langCount;
  }

  public Author getAuthor() {
    return author;
  }

  public int getBooksCount() {
    return booksCount;
  }

  public LocalDate getLastBook() {
    return lastBook;
  }

  public int getLangCount() {
    return langCount;
  }
}
