/**
 * Creates the Bored Ape ETH transfer events table: "tblTransferEvents"
 */
package com.example.nftboredape.scheduler.tasks;

import com.example.nftboredape.sql.TransferEventsTable;
import org.springframework.beans.factory.annotation.Autowired;

public class InitializeTransferEventsTable {

  @Autowired
  private TransferEventsTable transferEventsTable;

  public void execute() {
    try {
      transferEventsTable.create();
    } catch (Exception e) {
      // An exception will be thrown if the table already exists, but that's not an issue.
    }
    return;
  }
}
