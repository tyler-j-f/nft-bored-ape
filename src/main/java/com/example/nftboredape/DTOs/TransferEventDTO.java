/**
 * Data transfer object (DTO) for a single bored ape NFT transfer event. tokenId, transactionHash,
 * fromAddress, and toAddress will be stored as hex string values (This is how they are read from
 * the ETH blockchain with web3J).
 */
package com.example.nftboredape.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferEventDTO {
  private Long id;
  private String tokenId;
  private String transactionHash;
  private String fromAddress;
  private String toAddress;
  private Boolean isRead;
}
