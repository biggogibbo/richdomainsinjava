package com.roku.richdomains.domain;

public record AccountId(String value) {

  public static AccountId of(String value) {
    return new AccountId(value == null ? "" : value.trim());
  }

  public boolean isExternal() {
    return value != null && !value.startsWith("ACC-9");
  }
}
