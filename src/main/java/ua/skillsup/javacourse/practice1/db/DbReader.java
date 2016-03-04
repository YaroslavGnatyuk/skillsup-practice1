package ua.skillsup.javacourse.practice1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author leopold
 * @since 29/02/16
 */
public class DbReader<T> {

  private final DbClassAccessor<T> classAccessor;
  private final DbProps dbProps;

  public DbReader(Class<T> clazz, DbProps dbProps) {
    this.classAccessor = new DbClassAccessor<>(clazz);
    this.dbProps = dbProps;
  }

  public List<T> readAll() {
    return readList(null);
  }

  public List<T> readList(String where, Object... params) {

    final List<String> sqlColumns = classAccessor.getSqlFields();

    final StringBuilder sqlBuilder = new StringBuilder("SELECT ");
    sqlBuilder.append(sqlColumns.stream().collect(Collectors.joining(", ")));
    sqlBuilder.append(" FROM ").append(classAccessor.getTableName());

    if (where != null) {
      sqlBuilder.append(" WHERE ").append(where);
    }

    final List<Map<String, Object>> result = doQuery(sqlBuilder.toString(), sqlColumns, params);

    return result.stream()
        .map(classAccessor::mapFields)
        .collect(toList());
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

  private List<Map<String, Object>> doQuery(String query, List<String> columns, Object... params) {
    try (final Connection conn = DriverManager
        .getConnection(dbProps.url(), dbProps.user(), dbProps.password());
         final PreparedStatement pst = conn.prepareStatement(query)) {
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          pst.setObject(i + 1, params[i]);
        }
      }

      final ResultSet resultSet = pst.executeQuery();

      final ArrayList<Map<String, Object>> objects = new ArrayList<>();

      while (resultSet.next()) {

        final HashMap<String, Object> fieldsMap = new HashMap<>();

        for (String column : columns) {
          fieldsMap.put(column, resultSet.getObject(column));
        }

        objects.add(Collections.unmodifiableMap(fieldsMap));

      }

      return objects;
    } catch (SQLException e) {
      throw new DbException("Error reading SQL", e);
    }
  }
}
