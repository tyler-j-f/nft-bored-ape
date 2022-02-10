/**
 * Handles Bored Ape Transfer Events.
 *  - Reads the blockchain for the transfer events.
 *  - If there are events found, add them to the DB if they don't exist there.
 */
package com.example.nftboredape.scheduler.tasks;

import com.example.nftboredape.DTOs.TransferEventDTO;
import com.example.nftboredape.exceptions.EthEventException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleEthTransferEvents {

  @Autowired
  ReadTransferEvents readTransferEvents;
  @Autowired
  AddTransferEventsToDB addTransferEventsToDB;

  public void execute() throws EthEventException {
    List<TransferEventDTO> events = readTransferEvents.getEvents();
    System.out.println("DEBUG BLOCKCHAIN EVENTS:\n " + events);
    if (events.isEmpty()) {
      System.out.println("No transfer events found");
      return;
    }
    addTransferEventsToDB.addToDB(events);
  }

}
