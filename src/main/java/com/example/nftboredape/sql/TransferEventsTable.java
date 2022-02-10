/**
 *
 */
package com.example.nftboredape.sql;

import org.springframework.jdbc.core.JdbcTemplate;

public class TransferEventsTable implements TableInterface {

  public static final String TABLE_NAME = "tblTransferEvents";
  /*
   * NOTE: 2083 is the max VARCHAR length for a URL on the internet explorer browser.
   * So let's make the max string lengths equal to that (we will likely not need that long of strings anyways).
   */
  private static final String CREATE_SQL =
      "CREATE TABLE "
          + TABLE_NAME
          + "(id int NOT NULL AUTO_INCREMENT, tokenId VARCHAR(2083), transactionHash VARCHAR(2083), fromAddress VARCHAR(2083), toAddress VARCHAR(2083), isRead BOOLEAN, PRIMARY KEY (id))";
  private static final String DELETE_SQL = "DROP TABLE " + TABLE_NAME;

  private final JdbcTemplate jdbcTemplate;

  public TransferEventsTable(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public boolean create() {
    this.jdbcTemplate.execute(CREATE_SQL);
    return true;
  }

  @Override
  public boolean delete() {
    this.jdbcTemplate.execute(DELETE_SQL);
    return true;
  }

}
