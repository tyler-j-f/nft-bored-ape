package com.example.nftboredape.sql;

import com.example.nftboredape.DTOs.TransferEventDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class TransferEventsRepository extends AbstractRepository<TransferEventDTO> {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper beanPropertyRowMapper;

  public static final String READ_SQL = "SELECT * FROM " + TransferEventsTable.TABLE_NAME;
  // CRUD SQL
  public static final String CREATE_SQL =
      "INSERT INTO " + TransferEventsTable.TABLE_NAME + " VALUES (null, ?, ?, ?, ?, ?)";
  public static final String READ_BY_ID_SQL =
      "SELECT * FROM " + TransferEventsTable.TABLE_NAME + " WHERE tokenId = ?";
  public static final String UPDATE_BASE_SQL = "UPDATE " + TransferEventsTable.TABLE_NAME + " set ";
  public static final String UPDATE_SQL =
      "UPDATE "
          + TransferEventsTable.TABLE_NAME
          + " set tokenId = ?, transactionHash = ?, fromAddress = ?, toAddress = ?, isRead = ? WHERE id = ?";
  public static final String DELETE_BY_ID_SQL =
      "DELETE FROM " + TransferEventsTable.TABLE_NAME + " WHERE tokenId = ?";

  public TransferEventsRepository(JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    super(jdbcTemplate, TransferEventsTable.TABLE_NAME);
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  @Override
  public TransferEventDTO create(TransferEventDTO entity) {
    if (doesEventIdExist(entity)) {
      return null;
    }
    int results =
        jdbcTemplate.update(
            CREATE_SQL,
            entity.getTokenId(),
            entity.getTransactionHash(),
            entity.getToAddress(),
            entity.getFromAddress(),
            false
        );
    if (results != 1) {
      return null;
    }
    return readById(entity.getId());
  }

  @Override
  public List<TransferEventDTO> read() {
    Stream<TransferEventDTO> stream = null;
    try {
      stream = jdbcTemplate.queryForStream(READ_SQL, beanPropertyRowMapper);
      return stream.collect(Collectors.toList());
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  @Override
  public TransferEventDTO readById(Long transferEventId) {
    Stream<TransferEventDTO> stream = null;
    try {
      if (transferEventId == null) {
        return null;
      }
      stream = jdbcTemplate.queryForStream(READ_BY_ID_SQL, beanPropertyRowMapper, transferEventId);
      List<TransferEventDTO> tokens = stream.collect(Collectors.toList());
      if (tokens.size() == 0) {
        return null;
      }
      return tokens.get(0);
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  @Override
  public TransferEventDTO update(TransferEventDTO entity) {
    if (!doesEventIdExist(entity)) {
      return null;
    }
    List<Object> updateValuesList = new ArrayList<>();
    String updateSQL = UPDATE_BASE_SQL;
    // tokenId
    String tokenId = entity.getTokenId();
    boolean shouldUpdateTokenId = tokenId != null;
    boolean isCommaNeededToAppend = false;
    if (shouldUpdateTokenId) {
      updateSQL = updateSQL + "tokenId = ?";
      updateValuesList.add(tokenId);
      isCommaNeededToAppend = true;
    }
    // transactionHash
    String transactionHash = entity.getTransactionHash();
    boolean shouldUpdateTransactionHash = transactionHash != null;
    if (shouldUpdateTransactionHash) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "transactionHash = ?";
      updateValuesList.add(transactionHash);
      isCommaNeededToAppend = true;
    }
    // fromAddress
    String fromAddress = entity.getFromAddress();
    boolean shouldUpdateFromAddress = fromAddress != null;
    if (shouldUpdateFromAddress) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "fromAddress = ?";
      updateValuesList.add(fromAddress);
      isCommaNeededToAppend = true;
    }
    // toAddress
    String toAddress = entity.getToAddress();
    boolean shouldUpdateToAddress = toAddress != null;
    if (shouldUpdateToAddress) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "toAddress = ?";
      updateValuesList.add(toAddress);
      isCommaNeededToAppend = true;
    }
    // isRead
    Boolean isRead = entity.getIsRead();
    boolean shouldUpdateIsRead = isRead != null;
    if (shouldUpdateIsRead) {
      if (isCommaNeededToAppend) {
        updateSQL = updateSQL + ", ";
      }
      updateSQL = updateSQL + "isRead = ?";
      updateValuesList.add((boolean) isRead);
    }
    Long id = entity.getId();
    updateValuesList.add(id);
    updateSQL = updateSQL + " WHERE id = ?";
    int results = jdbcTemplate.update(updateSQL, updateValuesList.toArray());
    if (results < 1) {
      return null;
    }
    return readById(entity.getId());
  }

  @Override
  public boolean delete(TransferEventDTO entity) {
    if (!doesEventIdExist(entity)) {
      return false;
    }
    jdbcTemplate.update(DELETE_BY_ID_SQL, entity.getTokenId());
    return !doesEventIdExist(entity);
  }

  private boolean doesEventIdExist(TransferEventDTO entity) {
    return readById(entity.getId()) != null;
  }

}
