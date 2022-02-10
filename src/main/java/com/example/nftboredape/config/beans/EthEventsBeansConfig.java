package com.example.nftboredape.config.beans;

import com.example.nftboredape.config.external.EthConfig;
import com.example.nftboredape.scheduler.tasks.GetEthEventsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class EthEventsBeansConfig {

  @Autowired private EthConfig ethConfig;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(ethConfig.getAlchemyUri()));
  }

  @Bean
  public GetEthEventsTask getEthEventsTask() {
    return new GetEthEventsTask();
  }

}
