/**
 * Controller used to query for Bored Ape NFT transfer events.
 */

package com.example.nftboredape.controllers;

import com.example.nftboredape.DTOs.TransferEventDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/events"})
public class EventsController {

  /**
   * Get the transfer events by token id.
   *
   * @param tokenId Token id to get transfer events for
   * @return A JSON list of all events
   */
  @GetMapping(value = "tokenId/{tokenId}")
  public String getEventsByTokenId(@PathVariable Long tokenId) {
    List<TransferEventDTO> events = new ArrayList<>();
    events.add(
        TransferEventDTO.builder().tokenId("0x01").transactionHash("0x111").fromAddress("0x01").toAddress("0x02").build()
    );
    events.add(
        TransferEventDTO.builder().tokenId("0x02").transactionHash("0x222").fromAddress("0x02").toAddress("0x03").build()
    );
    return events.toString();
  }

  /**
   * Get the transfer events by address.
   *
   * @param address Address to get transfer events for
   * @return A JSON list of all events
   */
  @GetMapping(value = "address/{address}")
  public String getEventsByAddress(@PathVariable String address) {
    List<TransferEventDTO> events = new ArrayList<>();
    events.add(
        TransferEventDTO.builder().tokenId("0x01").transactionHash("0x111").fromAddress("0x01").toAddress("0x02").build()
    );
    events.add(
        TransferEventDTO.builder().tokenId("0x02").transactionHash("0x222").fromAddress("0x02").toAddress("0x03").build()
    );
    return events.toString();  }

}
