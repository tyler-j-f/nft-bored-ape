/**
 * Controller used to query for Bored Ape NFT transfer events. View the README to see how to
 * interact with the endpoints.
 */
package com.example.nftboredape.controllers;

import com.example.nftboredape.DTOs.TransferEventDTO;
import com.example.nftboredape.sql.TransferEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/events/update"})
public class UpdateEventsController {

  @Autowired private TransferEventsRepository transferEventsRepository;

  /**
   * Get the transfer events by token id.
   *
   * @param eventId Event SQL id to get transfer events for This SQL id value will be printed when
   *     this API is hit to display events by address or tokenId
   * @return A JSON list of all events
   */
  @GetMapping(value = "markEventAsRead/{eventId}")
  public String markEventAsRead(@PathVariable Long eventId) {
    TransferEventDTO event = transferEventsRepository.readById(eventId);
    if (event == null) {
      return "No event found for id: " + eventId;
    }
    event.setIsRead(true);
    transferEventsRepository.update(event);
    return "Successfully marked as read.";
  }
}
