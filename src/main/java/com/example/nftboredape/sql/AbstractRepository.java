/** Abstract repository. Can be used to create a repository of any DTO class. */
package com.example.nftboredape.sql;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractRepository<T> implements RepositoryInterface<T, Long> {
  protected static final String COUNT_BASE_SQL = "SELECT COUNT(*) FROM ";
  protected final JdbcTemplate jdbcTemplate;
  private String tableName;

  public AbstractRepository(JdbcTemplate jdbcTemplate, String tableName) {
    this.jdbcTemplate = jdbcTemplate;
    this.tableName = tableName;
  }

  private String getCountSql() {
    return COUNT_BASE_SQL + tableName;
  }

  public Long getCount() {
    return jdbcTemplate.queryForObject(getCountSql(), Long.class);
  }
}
