package com.example.nftboredape.sql;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.nftboredape.DTOs.TransferEventDTO;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class TransferEventsRepositoryTest {
  private JdbcTemplate jdbcTemplate;
  private BeanPropertyRowMapper beanPropertyRowMapper;
  // Value set 1
  private static long ID_1 = 1l;
  private static String TOKEN_ID_1 = "0x00";
  private static String TRANSACTION_HASH_1 = "0x01";
  private static String FROM_ADDRESS_1 = "0x02";
  private static String TO_ADDRESS_1 = "0x03";
  private static boolean IS_READ_1 = false;
  // Value set 2
  private static long ID_2 = 2l;
  private static String TOKEN_ID_2 = "0x04";
  private static String TRANSACTION_HASH_2 = "0x05";
  private static String FROM_ADDRESS_2 = "0x06";
  private static String TO_ADDRESS_2 = "0x07";
  private static boolean IS_READ_2 = true;

  @BeforeEach
  public void setup() {
    this.jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    this.beanPropertyRowMapper = new BeanPropertyRowMapper(TransferEventDTO.class);
  }

  @Test
  /** Test creating a non-existing entry on tblTransferEvents */
  void testCreateNonExistingEntry() {
    TransferEventDTO eventDTO =
        TransferEventDTO.builder()
            .id(ID_1)
            .tokenId(TOKEN_ID_1)
            .transactionHash(TRANSACTION_HASH_1)
            .fromAddress(FROM_ADDRESS_1)
            .toAddress(TO_ADDRESS_1)
            .isRead(IS_READ_1)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TransferEventsRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID_1))
        .thenReturn(Stream.empty(), Stream.of(eventDTO));
    Mockito.when(
            jdbcTemplate.update(
                TransferEventsRepository.CREATE_SQL,
                TOKEN_ID_1,
                TRANSACTION_HASH_1,
                FROM_ADDRESS_1,
                TO_ADDRESS_1,
                IS_READ_1))
        .thenReturn(1);
    Mockito.when(jdbcTemplate.queryForObject(Mockito.any(), (Class<Object>) Mockito.any()))
        .thenReturn(1L);
    TransferEventDTO eventDTOResult =
        new TransferEventsRepository(jdbcTemplate, beanPropertyRowMapper).create(eventDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TransferEventsRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID_1);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            TransferEventsRepository.CREATE_SQL,
            TOKEN_ID_1,
            TRANSACTION_HASH_1,
            FROM_ADDRESS_1,
            TO_ADDRESS_1,
            IS_READ_1);
    assertThat(eventDTOResult).isEqualTo(eventDTO);
  }

  @Test
  /** Test creating an existing entry on tblTransferEvents and make sure it does not work. */
  void testCreateExisting() {
    TransferEventDTO eventDTO =
        TransferEventDTO.builder()
            .id(ID_1)
            .tokenId(TOKEN_ID_1)
            .transactionHash(TRANSACTION_HASH_1)
            .fromAddress(FROM_ADDRESS_1)
            .toAddress(TO_ADDRESS_1)
            .isRead(IS_READ_1)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TransferEventsRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID_1))
        .thenReturn(Stream.of(eventDTO));
    Mockito.when(
            jdbcTemplate.update(
                TransferEventsRepository.CREATE_SQL,
                TOKEN_ID_1,
                TRANSACTION_HASH_1,
                FROM_ADDRESS_1,
                TO_ADDRESS_1,
                IS_READ_1))
        .thenReturn(1);
    Mockito.when(jdbcTemplate.queryForObject(Mockito.any(), (Class<Object>) Mockito.any()))
        .thenReturn(1L);
    TransferEventDTO eventDTOResult =
        new TransferEventsRepository(jdbcTemplate, beanPropertyRowMapper).create(eventDTO);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TransferEventsRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID_1);
    Mockito.verify(jdbcTemplate, Mockito.times(0))
        .update(
            TransferEventsRepository.CREATE_SQL,
            TOKEN_ID_1,
            TRANSACTION_HASH_1,
            FROM_ADDRESS_1,
            TO_ADDRESS_1,
            IS_READ_1);
    assertThat(eventDTOResult).isEqualTo(null);
  }

  /** Mock 2 event DTOs to return and assert that they are returned when calling read */
  @Test
  void testRead() {
    TransferEventDTO eventDTO =
        TransferEventDTO.builder()
            .id(ID_1)
            .tokenId(TOKEN_ID_1)
            .transactionHash(TRANSACTION_HASH_1)
            .fromAddress(FROM_ADDRESS_1)
            .toAddress(TO_ADDRESS_1)
            .isRead(IS_READ_1)
            .build();
    TransferEventDTO eventDTO2 =
        TransferEventDTO.builder()
            .id(ID_2)
            .tokenId(TOKEN_ID_2)
            .transactionHash(TRANSACTION_HASH_2)
            .fromAddress(FROM_ADDRESS_2)
            .toAddress(TO_ADDRESS_2)
            .isRead(IS_READ_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(TransferEventsRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.of(eventDTO, eventDTO2));
    List<TransferEventDTO> tokens =
        new TransferEventsRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TransferEventsRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(tokens.get(0)).isEqualTo(eventDTO);
    assertThat(tokens.get(1)).isEqualTo(eventDTO2);
  }

  /** Assert that reading from an empty table does not return any DTO entries. */
  @Test
  void testReadEmptyTable() {
    Mockito.when(
            jdbcTemplate.queryForStream(TransferEventsRepository.READ_SQL, beanPropertyRowMapper))
        .thenReturn(Stream.empty());
    List<TransferEventDTO> tokens =
        new TransferEventsRepository(jdbcTemplate, beanPropertyRowMapper).read();
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TransferEventsRepository.READ_SQL, beanPropertyRowMapper);
    assertThat(tokens.isEmpty()).isEqualTo(true);
  }

  /** Assert that reading an existing entry by id (primary key) returns the correct event DTO. */
  @Test
  void testReadExistingById() {
    TransferEventDTO eventDTO =
        TransferEventDTO.builder()
            .id(ID_1)
            .tokenId(TOKEN_ID_1)
            .transactionHash(TRANSACTION_HASH_1)
            .fromAddress(FROM_ADDRESS_1)
            .toAddress(TO_ADDRESS_1)
            .isRead(IS_READ_1)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TransferEventsRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID_1))
        .thenReturn(Stream.of(eventDTO));
    TransferEventDTO tokenDTOResult =
        new TransferEventsRepository(jdbcTemplate, beanPropertyRowMapper).readById(ID_1);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TransferEventsRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID_1);
    assertThat(tokenDTOResult).isEqualTo(eventDTO);
  }

  /** Test updating an existing table entry and assert that the updated values are returned. */
  @Test
  void testUpdateExistingEntry() {
    TransferEventDTO eventDTO =
        TransferEventDTO.builder()
            .id(ID_1)
            .tokenId(TOKEN_ID_1)
            .transactionHash(TRANSACTION_HASH_1)
            .fromAddress(FROM_ADDRESS_1)
            .toAddress(TO_ADDRESS_1)
            .isRead(IS_READ_1)
            .build();
    // eventDTO2 Would always have the same id and token id as tokenDTO
    TransferEventDTO eventDTO2 =
        TransferEventDTO.builder()
            .id(ID_1)
            .tokenId(TOKEN_ID_1)
            .transactionHash(TRANSACTION_HASH_2)
            .fromAddress(FROM_ADDRESS_2)
            .toAddress(TO_ADDRESS_2)
            .isRead(IS_READ_2)
            .build();
    Mockito.when(
            jdbcTemplate.queryForStream(
                TransferEventsRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID_1))
        .thenReturn(Stream.of(eventDTO), Stream.of(eventDTO2));
    Mockito.when(
            jdbcTemplate.update(
                TransferEventsRepository.UPDATE_SQL,
                TOKEN_ID_1,
                TRANSACTION_HASH_2,
                FROM_ADDRESS_2,
                TO_ADDRESS_2,
                IS_READ_2,
                ID_1))
        .thenReturn(1);
    TransferEventDTO tokenDTOResults =
        new TransferEventsRepository(jdbcTemplate, beanPropertyRowMapper).update(eventDTO2);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .queryForStream(TransferEventsRepository.READ_BY_ID_SQL, beanPropertyRowMapper, ID_1);
    Mockito.verify(jdbcTemplate, Mockito.times(1))
        .update(
            TransferEventsRepository.UPDATE_SQL,
            TOKEN_ID_1,
            TRANSACTION_HASH_2,
            FROM_ADDRESS_2,
            TO_ADDRESS_2,
            IS_READ_2,
            ID_1);
    assertThat(tokenDTOResults).isEqualTo(eventDTO2);
  }
}
