package com.example.nftboredape.exceptions;

public class EthEventException extends Exception {

  public EthEventException(String message) {
    super(message);
  }

  public EthEventException(String message, Throwable cause) {
    super(message, cause);
  }
}
