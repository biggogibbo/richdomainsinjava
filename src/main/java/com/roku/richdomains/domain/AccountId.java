package com.roku.richdomains.domain;

public record AccountId(String value) {

  public static AccountId of(String value) {
    return new AccountId(value);
  }

  public boolean isInternal() {
    return value.startsWith("ACC-9");
  }

  public boolean isValid() {
    return value != null && !value.isBlank() && value.matches("ACC-\\d{8}");
  }

}
