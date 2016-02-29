package ua.skillsup.javacourse.practice1.model;

import java.time.LocalDate;

import ua.skillsup.javacourse.practice1.db.Column;
import ua.skillsup.javacourse.practice1.db.Table;

/**
 * @author leopold
 * @since 27/02/16
 */
@Table("books")
public class Book {

  @Column
  private Integer id;

  @Column
  private String title;

  @Column("author_id")
  private Integer authorId;

  @Column
  private String originalLanguage;

  @Column
  private String isbn;

  @Column
  private LocalDate published;

  @Column("abstract")
  private String summary;

  @Column("avg_rating")
  private Double avgRating;

  @Column("reviews_count")
  private Integer reviewsCount;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public LocalDate getPublished() {
    return published;
  }

  public void setPublished(LocalDate published) {
    this.published = published;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public Double getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(Double avgRating) {
    this.avgRating = avgRating;
  }

  public Integer getReviewsCount() {
    return reviewsCount;
  }

  public void setReviewsCount(Integer reviewsCount) {
    this.reviewsCount = reviewsCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Book book = (Book) o;

    if (id != null ? !id.equals(book.id) : book.id != null) {
      return false;
    }
    if (!title.equals(book.title)) {
      return false;
    }
    if (authorId != null ? !authorId.equals(book.authorId) : book.authorId != null) {
      return false;
    }
    if (originalLanguage != null ? !originalLanguage.equals(book.originalLanguage)
                                 : book.originalLanguage != null) {
      return false;
    }
    if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) {
      return false;
    }
    if (!published.equals(book.published)) {
      return false;
    }
    return summary != null ? summary.equals(book.summary) : book.summary == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + title.hashCode();
    result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
    result = 31 * result + (originalLanguage != null ? originalLanguage.hashCode() : 0);
    result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
    result = 31 * result + published.hashCode();
    result = 31 * result + (summary != null ? summary.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Book{" +
           "title='" + title + '\'' +
           ", id='" + id + '\'' +
           ", originalLanguage='" + originalLanguage + '\'' +
           ", isbn='" + isbn + '\'' +
           ", published=" + published +
//           ", summary='" + summary + '\'' +
           '}';
  }
}
