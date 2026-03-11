package com.roku.richdomains.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public final class AccountId {
  private static final Pattern VALID_FORMAT = Pattern.compile("ACC-\\d{8}");
  private static final String BLOCKED_PREFIX = "ACC-9";

  private final String value;

  private AccountId(String value) {
    this.value = value;
  }

  public static AccountId of(String value) {
    Objects.requireNonNull(value, "Account ID cannot be null");
    if (value.isBlank()) {
      throw new IllegalArgumentException("Account ID cannot be blank");
    }
    if (!VALID_FORMAT.matcher(value).matches()) {
      throw new IllegalArgumentException(
          "Account ID must match format ACC-XXXXXXXX where X is a digit: " + value);
    }
    return new AccountId(value);
  }

  public static AccountId ofNullable(String value) {
    if (value == null || value.isBlank() || !VALID_FORMAT.matcher(value).matches()) {
      return null;
    }
    return new AccountId(value);
  }

  public boolean isBlocked() {
    return value.startsWith(BLOCKED_PREFIX);
  }

  public boolean isExternal() {
    return !isBlocked();
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AccountId accountId = (AccountId) o;
    return value.equals(accountId.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value;
  }
}
