package ua.skillsup.javacourse.practice1.prices;

import java.util.List;

import ua.skillsup.javacourse.practice1.model.Book;
import ua.skillsup.javacourse.practice1.model.BookDao;

/**
 * @author leopold
 * @since 4/03/16
 */
public class BookPriceService {

  private final BookDao bookDao;

  private final PriceProvider provider1;
  private final PriceProvider provider2;

  public BookPriceService(BookDao bookDao,
                          PriceProvider provider1,
                          PriceProvider provider2) {
    this.bookDao = bookDao;
    this.provider1 = provider1;
    this.provider2 = provider2;
  }

  public Double getBestPrice(String author, String bookName) {

    final List<Book> booksForAuthor = bookDao.getBooksForAuthor(author);

    if (booksForAuthor.isEmpty()) {
      return null;
    }

    final Book book = booksForAuthor.stream()
        .filter(b -> b.getTitle().contains(bookName))
        .findAny()
        .orElse(null);

    if (book == null || book.getIsbn() == null) {
      return null;
    }

    final Double price1 = provider1.getPrice(new PriceProvider.BookInfo(book.getIsbn()));
    final Double price2 = provider2.getPrice(new PriceProvider.BookInfo(book.getIsbn()));

    if (price1 == null) {
      return price2;
    }

    if (price2 == null) {
      return price1;
    }

    return Math.min(price1, price2);


  }
}
