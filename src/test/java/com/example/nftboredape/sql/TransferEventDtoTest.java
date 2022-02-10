package com.example.nftboredape.sql;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.nftboredape.DTOs.TransferEventDTO;
import org.junit.jupiter.api.Test;

public class TransferEventDtoTest {
  // Value set 1
  private static long ID_1                 = 1l;
  private static String TOKEN_ID_1         = "0x00";
  private static String TRANSACTION_HASH_1 = "0x01";
  private static String FROM_ADDRESS_1     = "0x02";
  private static String TO_ADDRESS_1       = "0x03";
  private static boolean IS_READ_1         = false;
  // Value set 2
  private static long ID_2                 = 2l;
  private static String TOKEN_ID_2         = "0x04";
  private static String TRANSACTION_HASH_2 = "0x05";
  private static String FROM_ADDRESS_2     = "0x06";
  private static String TO_ADDRESS_2       = "0x07";
  private static boolean IS_READ_2         = true;

  /**
   * Test the DTO is wired correctly via the constructor.
   */
  @Test
  void testConstructor() {
    // Create with value set 1.
    TransferEventDTO eventDTO =
        new TransferEventDTO(
            ID_1,
            TOKEN_ID_1,
            TRANSACTION_HASH_1,
            FROM_ADDRESS_1,
            TO_ADDRESS_1,
            IS_READ_1
        );
    // Assert that getters return value set 1.
    assertThat(eventDTO.getId()).isEqualTo(ID_1);
    assertThat(eventDTO.getTokenId()).isEqualTo(TOKEN_ID_1);
    assertThat(eventDTO.getTransactionHash()).isEqualTo(TRANSACTION_HASH_1);
    assertThat(eventDTO.getFromAddress()).isEqualTo(FROM_ADDRESS_1);
    assertThat(eventDTO.getToAddress()).isEqualTo(TO_ADDRESS_1);
    assertThat(eventDTO.getIsRead()).isEqualTo(IS_READ_1);
  }

  /**
   * Test updating the DTO with the set methods and test that the updates occurred on the object.
   */
  @Test
  void testUpdates() {
    // Create with value set 1 initially.
    TransferEventDTO eventDTO =
        new TransferEventDTO(
            ID_1,
            TOKEN_ID_1,
            TRANSACTION_HASH_1,
            FROM_ADDRESS_1,
            TO_ADDRESS_1,
            IS_READ_1
        );
    // Set value set 2.
    eventDTO.setId(ID_2);
    eventDTO.setTokenId(TOKEN_ID_2);
    eventDTO.setTransactionHash(TRANSACTION_HASH_2);
    eventDTO.setFromAddress(FROM_ADDRESS_2);
    eventDTO.setToAddress(TO_ADDRESS_2);
    eventDTO.setIsRead(IS_READ_2);
    // Assert that getters return value set 2.
    assertThat(eventDTO.getId()).isEqualTo(ID_2);
    assertThat(eventDTO.getTokenId()).isEqualTo(TOKEN_ID_2);
    assertThat(eventDTO.getTransactionHash()).isEqualTo(TRANSACTION_HASH_2);
    assertThat(eventDTO.getFromAddress()).isEqualTo(FROM_ADDRESS_2);
    assertThat(eventDTO.getToAddress()).isEqualTo(TO_ADDRESS_2);
    assertThat(eventDTO.getIsRead()).isEqualTo(IS_READ_2);
  }

  /**
   * Test the TransferEventDTOBuilder class
   */
  @Test
  void testBuilder() {
    TransferEventDTO.TransferEventDTOBuilder builder =
        TransferEventDTO.builder()
            .id(ID_1)
            .tokenId(TOKEN_ID_1)
            .transactionHash(TRANSACTION_HASH_1)
            .fromAddress(FROM_ADDRESS_1)
            .toAddress(TO_ADDRESS_1)
            .isRead(IS_READ_1);
    assertThat(builder).isInstanceOf(TransferEventDTO.TransferEventDTOBuilder.class);
    TransferEventDTO eventDTO = builder.build();
    assertThat(eventDTO).isInstanceOf(TransferEventDTO.class);
    // Assert that getters return value set 1.
    assertThat(eventDTO.getId()).isEqualTo(ID_1);
    assertThat(eventDTO.getTokenId()).isEqualTo(TOKEN_ID_1);
    assertThat(eventDTO.getTransactionHash()).isEqualTo(TRANSACTION_HASH_1);
    assertThat(eventDTO.getFromAddress()).isEqualTo(FROM_ADDRESS_1);
    assertThat(eventDTO.getToAddress()).isEqualTo(TO_ADDRESS_1);
    assertThat(eventDTO.getIsRead()).isEqualTo(IS_READ_1);
  }
}
