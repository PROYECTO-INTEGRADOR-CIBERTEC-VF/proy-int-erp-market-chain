package com.tiendasgo.catalog.exceptions;

public class InvalidPriceException extends RuntimeException {
  public InvalidPriceException(String message) {
    super(message);
  }
}
