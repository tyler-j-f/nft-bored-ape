/**
 * External config. The values will be injected at runtime.
 *
 * <p>Can update the values by modifying the config at: application.yaml (spring.application.eth)
 */
package com.example.nftboredape.config.external;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.application.eth")
@Data
public class EthConfig {
  private String alchemyApiKey;
  private String alchemyBaseUrl;
  private String contractAddress;
  private String eventHashSignature;
  private Long numberOfBlocksAgo;

  public String getAlchemyUri() {
    return alchemyBaseUrl + alchemyApiKey;
  }
}
