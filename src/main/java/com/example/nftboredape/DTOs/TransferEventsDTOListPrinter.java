package com.example.nftboredape.DTOs;

import java.util.List;

public class TransferEventsDTOListPrinter {

  public String print(List<TransferEventDTO> events) {
    String output = "Events:";
    Long count = 0L;
    for (TransferEventDTO event : events) {
      count++;
      output = output + "\n\nEvent " + count + ":\n" + event.toString();
    }
    return output;
  }
}
