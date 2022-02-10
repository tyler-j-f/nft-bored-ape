/**
 * Reads the Bored Ape NFT transfer events from the ETH mainnet. Update the config
 * (application.yaml) to change values for: - Contract address (Won't change for the ETH mainnet,
 * but can be updated to work on another deployment of Bored Ape smart contracts) - Alchemy API Key
 * - Contract address (Won't change for the ETH mainnet, but can be updated to work on another
 * deployment of Bored Ape smart contracts) - ETH event hash (Won't change for the ETH mainnet, but
 * can be updated to work on another deployment of Bored Ape smart contracts)
 *
 * <p>Here is an example ETH mainnet transaction that had a "Transfer" event emitted (tokenId =>
 * 9440) https://etherscan.io/tx/0x285491453a141f95121020d42fd81a6bbd7bc3808d6b93cf8faa9a0c8f500f5c
 */
package com.example.nftboredape.scheduler.tasks;

import com.example.nftboredape.DTOs.TransferEventDTO;
import com.example.nftboredape.config.external.EthConfig;
import com.example.nftboredape.exceptions.EthEventException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;

public class ReadTransferEvents {

  @Autowired private EthConfig ethConfig;
  @Autowired private Web3j web3j;

  /**
   * Scans the ETH blockchain for Bored Ape NFT transfer events. View the Bored Ape NFT smart
   * contract code here:
   * https://etherscan.io/address/0xbc4ca0eda7647a8ab7c2061c2e118a18a936f13d#code
   *
   * <p>In the Solidity code we will see: event Transfer(address indexed from, address indexed to,
   * uint256 indexed tokenId);
   *
   * <p>This is the Solidity event we will be looking for.
   *
   * @return A list of transfer event DTOs
   * @throws EthEventException
   */
  public List<TransferEventDTO> getEvents() throws EthEventException {
    try {
      BigInteger currentBlockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
      DefaultBlockParameter fromBlock =
          DefaultBlockParameter.valueOf(
              currentBlockNumber.subtract(BigInteger.valueOf(ethConfig.getNumberOfBlocksAgo())));
      EthFilter filter =
          new EthFilter(
              fromBlock,
              DefaultBlockParameter.valueOf(currentBlockNumber),
              ethConfig.getContractAddress());
      List<EthLog.LogResult> logs = web3j.ethGetLogs(filter).sendAsync().get().getLogs();
      if (logs.size() == 0) {
        return new ArrayList<>();
      }
      return createEventDTOs(logs);
    } catch (ExecutionException | InterruptedException e) {
      throw new EthEventException(e.getMessage(), e.getCause());
    }
  }

  /**
   * Create a list of event DTOs for the inputted event logs.
   *
   * @param logs The Bored Ape NFT transfer logs
   * @return A list of event DTOs
   */
  private List<TransferEventDTO> createEventDTOs(List<EthLog.LogResult> logs) {
    List<TransferEventDTO> events = new ArrayList<>();
    for (EthLog.LogResult log : logs) {
      List<String> topics = ((Log) log).getTopics();
      if (topics.get(0).equals(ethConfig.getEventHashSignature())) {
        TransferEventDTO event =
            TransferEventDTO.builder()
                .transactionHash(((Log) log).getTransactionHash())
                .fromAddress(topics.get(1))
                .toAddress(topics.get(2))
                .tokenId(topics.get(3))
                .build();
        events.add(event);
      }
    }
    return events;
  }
}
