package ua.skillsup.javacourse.practice1.db;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author leopold
 * @since 29/02/16
 */
public class DbReader<T> {

  private final Class<T> clazz;
  private final Map<String, Field> fields;
  private final String tableName;
  private final DbProps dbProps;

  public DbReader(Class<T> clazz, DbProps dbProps) {
    this.clazz = clazz;
    this.dbProps = dbProps;

    // todo: fill this fields by processing @Column and @Table annotations of the class.
    this.tableName = null;
    this.fields = Collections.emptyMap();
  }

  public List<T> readList(String where, Object... params) {
    // todo: needs to be implemented using reflection API.
    throw new UnsupportedOperationException();
  }

  public T readSingle(String where, Object params) {
    final List<T> result = readList(where, params);
    if (result.size() > 1) {
      throw new IllegalArgumentException("Single object query returned multiple results");
    }
    return result.isEmpty() ? null : result.get(0);
  }

  public T readFirst(String where, Object params) {
    final List<T> result = readList(where, params);
    return result.isEmpty() ? null : result.get(0);
  }

  public T readById(int id) {
    return readSingle("id = ?", id);
  }

}
