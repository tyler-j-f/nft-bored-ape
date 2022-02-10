/**
 * Data transfer object (DTO) for a single bored ape NFT transfer event.
 */

package com.example.nftboredape.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDTO {
  private Long tokenId;
  private String transactionId;
  private String fromAddress;
  private String toAddress;
}
