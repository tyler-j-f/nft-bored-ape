package com.example.nftboredape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NftBoredApeApplication {

  /**
   * Start the application.
   *
   * <p>View the "Scheduler" class to see how Bored Ape transfer events are read from the blockchain
   * and added to the DB.
   *
   * <p>View the "EventsRetrieverController" class to see how API requests to query for events are
   * handled.
   *
   * <p>View the "isReadController" class to see how API requests to mark an event as read are
   * handled.
   *
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(NftBoredApeApplication.class, args);
  }
}
