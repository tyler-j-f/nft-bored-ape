/**
 * Scheduler class. Runs intermittently to scan the blockchain for transfer events then add those
 * events to the DB.
 */
package com.example.nftboredape.scheduler;

import com.example.nftboredape.exceptions.EthEventException;
import com.example.nftboredape.scheduler.tasks.DeleteTransferEventsTable;
import com.example.nftboredape.scheduler.tasks.HandleEthTransferEvents;
import com.example.nftboredape.scheduler.tasks.InitializeTransferEventsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

  @Autowired private Environment env;
  @Autowired private HandleEthTransferEvents handleEthTransferEvents;
  @Autowired InitializeTransferEventsTable initializeTransferEventsTable;
  @Autowired DeleteTransferEventsTable deleteTransferEventsTable;
  private boolean hasSqlTableBeenInitialized = false;
  /**
   * Scans the ETH blockchain for Bored Ape NFT transfer events. Adds the events to the DB to be
   * retrieved later.
   *
   * <p>The "@Scheduled" annotation is used to run a "scheduled task":
   * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/Scheduled.html
   *
   * <p>fixedDelayString determines how often this task is ran. fixedDelayString can be updated from
   * the config file: src/main/resources/application.yaml
   */
  @Scheduled(
      fixedDelayString = "${spring.application.scheduler-config.schedulerFixedDelayStringMs}")
  public void runScheduledTask() throws EthEventException {
    if (shouldResetSqlTables() && !hasSqlTableBeenInitialized) {
      deleteTransferEventsTable.execute();
    }
    if (!hasSqlTableBeenInitialized) {
      initializeTransferEventsTable.execute();
      hasSqlTableBeenInitialized = true;
    }
    handleEthTransferEvents.execute();
  }

  private boolean shouldResetSqlTables() {
    return Boolean.parseBoolean(env.getProperty("shouldResetSqlTables"));
  }
}
