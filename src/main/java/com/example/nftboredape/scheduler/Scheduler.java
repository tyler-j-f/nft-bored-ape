package com.example.nftboredape.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

  /**
   * Scans the ETH blockchain for Bored Ape NFT transfer events.
   * Adds the events to the DB to be retrieved later.
   * fixedDelayString determines how often this is ran.
   * fixedDelayString can be updated from the config file: src/main/resources/application.yaml
   */
  @Scheduled(fixedDelayString = "${spring.application.events-config.schedulerFixedDelayStringMs}")
  public void scanForTransferEvents() {

  }
}
