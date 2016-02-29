package ua.skillsup.javacourse.practice1.db;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * @author leopold
 * @since 29/02/16
 */
public class DbClassAccessor<T> {

  private final Class<T> dbClass;
  private final String tableName;
  private final Map<String, Field> classFields;

  public DbClassAccessor(Class<T> dbClass) {
    this.dbClass = dbClass;
    this.tableName = configureTableName();
    this.classFields = configureClassFields();
  }

  public String getTableName() {
    return tableName;
  }

  public List<String> getSqlFields() {
    return new ArrayList<>(classFields.keySet());
  }

  public T mapFields(Map<String, Object> fields) {
    final T object;
    try {
      object = dbClass.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new DbException("Cannon instatiate " + dbClass, e);
    }

    fields.forEach((column, value) -> {
      final Field field = classFields.get(column);

      field.setAccessible(true);
      try {
        field.set(object, value instanceof Date ? ((Date) value).toLocalDate() : value);
      } catch (IllegalAccessException e) {
        throw new DbException("Cannot set field " + field, e);
      }

    });

    return object;
  }

  private String configureTableName() {
    final Table tableAnn = dbClass.getAnnotation(Table.class);
    if (tableAnn == null) {
      throw new DbException("@Table annotation is absent on class " + dbClass);
    }

    return tableAnn.value();
  }

  private Map<String, Field> configureClassFields() {
    return Stream.of(dbClass.getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(Column.class))
        .collect(toMap(
            DbClassAccessor::getColumnName, // key
            Function.identity(), // value
            (f1, f2) -> {
              throw new DbException("Dublicate keys: " + f1);
            },
            LinkedHashMap::new
        ));
  }

  private static String getColumnName(Field field) {
    final String annValue = field.getAnnotation(Column.class).value();
    return "".equals(annValue) ? field.getName() : annValue;
  }
}
