/**
 * Data transfer object (DTO) for a single bored ape NFT transfer event.
 */

package com.example.nftboredape.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDTO {
  private String tokenId;
  private String transactionHAsh;
  private String fromAddress;
  private String toAddress;
}
