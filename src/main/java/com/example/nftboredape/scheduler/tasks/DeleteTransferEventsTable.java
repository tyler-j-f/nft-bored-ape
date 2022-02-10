package com.example.nftboredape.scheduler.tasks;

import com.example.nftboredape.sql.TransferEventsTable;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteTransferEventsTable {

  @Autowired private TransferEventsTable transferEventsTable;

  public void execute() {
    try {
      transferEventsTable.delete();
    } catch (Exception e) {
      // An exception will be thrown if the table does not exist, but that's not an issue.
    }
    return;
  }
}
