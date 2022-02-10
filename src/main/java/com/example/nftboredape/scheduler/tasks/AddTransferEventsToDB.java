/** Adds transfer events to the DB. Will not add an event to the DB if it already exists. */
package com.example.nftboredape.scheduler.tasks;

import com.example.nftboredape.DTOs.TransferEventDTO;
import com.example.nftboredape.sql.TransferEventsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class AddTransferEventsToDB {

  @Autowired private TransferEventsRepository transferEventsRepository;

  public void addToDB(List<TransferEventDTO> events) {
    for (TransferEventDTO event : events) {
      if (isEventAlreadyInDB(event)) {
        System.out.println("event already exists in the db. event: " + event);
        return;
      }
      transferEventsRepository.create(event);
    }
  }

  private boolean isEventAlreadyInDB(TransferEventDTO event) {
    return transferEventsRepository.readByDuplicateRelatedData(event) != null;
  }
}
