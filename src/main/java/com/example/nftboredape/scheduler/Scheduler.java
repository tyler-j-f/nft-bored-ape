package com.example.nftboredape.scheduler;

import com.example.nftboredape.exceptions.EthEventException;
import com.example.nftboredape.scheduler.tasks.GetEthEventsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

  @Autowired
  private GetEthEventsTask getEthEventsTask;

  /**
   * Scans the ETH blockchain for Bored Ape NFT transfer events.
   * Adds the events to the DB to be retrieved later.
   * fixedDelayString determines how often this is ran.
   * fixedDelayString can be updated from the config file: src/main/resources/application.yaml
   */
  @Scheduled(fixedDelayString = "${spring.application.scheduler-config.schedulerFixedDelayStringMs}")
  public void scanForTransferEvents() throws EthEventException {
    System.out.println(
        getEthEventsTask.getEvents()
    );
  }
}
