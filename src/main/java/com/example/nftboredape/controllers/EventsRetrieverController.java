/**
 * Controller used to query to retrieve Bored Ape NFT transfer events. View the README to see how to
 * interact with the endpoints.
 */
package com.example.nftboredape.controllers;

import com.example.nftboredape.DTOs.TransferEventDTO;
import com.example.nftboredape.DTOs.TransferEventsDTOListPrinter;
import com.example.nftboredape.sql.TransferEventsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/events/get"})
public class EventsRetrieverController {

  @Autowired private TransferEventsRepository transferEventsRepository;
  @Autowired private TransferEventsDTOListPrinter eventsListPrinter;

  /** @return A list of all transfer events that have been added to the SQL DB */
  @GetMapping(value = "all")
  public String getAllEvents() {
    List<TransferEventDTO> events = transferEventsRepository.read();
    if (events.isEmpty()) {
      return "No events not marked as read found.";
    }
    return eventsListPrinter.print(events);
  }

  /**
   * Get the transfer events by token id.
   *
   * @param tokenId Token id to get transfer events for
   * @return A JSON list of all events
   */
  @GetMapping(value = "tokenId/{tokenId}")
  public String getEventsByTokenId(@PathVariable String tokenId) {
    List<TransferEventDTO> events = transferEventsRepository.readByTokenId(tokenId);
    if (events.isEmpty()) {
      return "No events found for tokenId: " + tokenId;
    }
    return eventsListPrinter.print(events);
  }

  /**
   * Get the transfer events by address. Sent or received token address from transfer events will be
   * read.
   *
   * @param address Address to get transfer events for
   * @return A JSON list of all events
   */
  @GetMapping(value = "address/{address}")
  public String getEventsByAddress(@PathVariable String address) {
    List<TransferEventDTO> events = transferEventsRepository.readByAddress(address);
    if (events.isEmpty()) {
      return "No events found for address: " + address;
    }
    return eventsListPrinter.print(events);
  }

  /** @return A list of all events that have not been marked as read in the SQL DB */
  @GetMapping(value = "unread")
  public String getUnreadEvents() {
    List<TransferEventDTO> events = transferEventsRepository.readByUnread();
    if (events.isEmpty()) {
      return "No events not marked as read found.";
    }
    return eventsListPrinter.print(events);
  }
}
