package ua.skillsup.javacourse.practice1.prices;

/**
 * @author leopold
 * @since 4/03/16
 */
public interface PriceProvider {

  Double getPrice(BookInfo bookInfo);

  class BookInfo {
    private String isbn;

    public String getIsbn() {
      return isbn;
    }

    public BookInfo(String isbn) {
      this.isbn = isbn;
    }

    public BookInfo() {
    }
  }

}
