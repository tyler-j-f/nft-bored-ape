/**
 * Setups spring beans related to ETH events/Web3J for dependency injection.
 * https://www.baeldung.com/spring-bean
 */
package com.example.nftboredape.config.beans;

import com.example.nftboredape.config.external.EthConfig;
import com.example.nftboredape.scheduler.tasks.AddTransferEventsToDB;
import com.example.nftboredape.scheduler.tasks.DeleteTransferEventsTable;
import com.example.nftboredape.scheduler.tasks.HandleEthTransferEvents;
import com.example.nftboredape.scheduler.tasks.InitializeTransferEventsTable;
import com.example.nftboredape.scheduler.tasks.ReadTransferEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class EthEventsBeans {

  @Autowired private EthConfig ethConfig;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(ethConfig.getAlchemyUri()));
  }

  @Bean
  public HandleEthTransferEvents getHandleTransferEvents() {
    return new HandleEthTransferEvents();
  }

  @Bean
  public ReadTransferEvents getEthEvents() {
    return new ReadTransferEvents();
  }

  @Bean
  public AddTransferEventsToDB getAddTransferEventsToDB() {
    return new AddTransferEventsToDB();
  }

  @Bean
  public InitializeTransferEventsTable getInitializeDB() {
    return new InitializeTransferEventsTable();
  }

  @Bean
  public DeleteTransferEventsTable getDeleteTransferEventsTable() {
    return new DeleteTransferEventsTable();
  }
}
