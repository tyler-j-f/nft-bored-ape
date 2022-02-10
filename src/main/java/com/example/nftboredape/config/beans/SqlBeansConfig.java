package com.example.nftboredape.config.beans;

import com.example.nftboredape.config.external.EthConfig;
import com.example.nftboredape.scheduler.tasks.GetEthEventsTask;
import com.example.nftboredape.sql.TransferEventsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class SqlBeansConfig {

  @Autowired private JdbcTemplate jdbcTemplate;

  @Bean
  public TransferEventsTable transferEventsTable() {
    return new TransferEventsTable(jdbcTemplate);
  }

}
