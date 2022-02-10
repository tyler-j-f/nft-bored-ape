/**
 * Setups spring beans related to SQL for dependency injection. https://www.baeldung.com/spring-bean
 */
package com.example.nftboredape.config.beans;

import com.example.nftboredape.DTOs.TransferEventDTO;
import com.example.nftboredape.sql.TransferEventsRepository;
import com.example.nftboredape.sql.TransferEventsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SqlBeans {

  @Autowired private JdbcTemplate jdbcTemplate;

  @Bean
  public TransferEventsTable transferEventsTable() {
    return new TransferEventsTable(jdbcTemplate);
  }

  @Bean
  TransferEventsRepository transferEventsRepository() {
    return new TransferEventsRepository(
        jdbcTemplate, new BeanPropertyRowMapper(TransferEventDTO.class));
  }
}
