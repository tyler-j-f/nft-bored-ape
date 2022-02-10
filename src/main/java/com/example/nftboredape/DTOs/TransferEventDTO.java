/**
 * Data transfer object (DTO) for a single bored ape NFT transfer event.
 * tokenId, transactionHash, fromAddress, and toAddress will be stored as hex string values (This is how they are read from the ETH blockchain with web3J).
 */

package com.example.nftboredape.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferEventDTO {
  private Long id;
  private String tokenId;
  private String transactionHash;
  private String fromAddress;
  private String toAddress;
  private boolean isRead = false;
}
