package com.roku.richdomains.domain;

public record CustomerId(String value) {

  public static CustomerId of(String value) {
    return new CustomerId(value == null ? "" : value.trim());
  }
}
