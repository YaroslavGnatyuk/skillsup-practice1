package ua.skillsup.javacourse.practice1.prices;

import java.util.Random;

/**
 * @author leopold
 * @since 4/03/16
 */
public class PriceProviderImpl implements PriceProvider {

  @Override
  public Double getPrice(BookInfo bookInfo) {
    return new Random().nextDouble() * 20;
  }
}
