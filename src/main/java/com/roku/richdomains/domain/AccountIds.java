package com.roku.richdomains.domain;

import java.util.List;

public record AccountIds(List<AccountId> values) {

  public AccountIds {
    values = List.copyOf(values); // Immutable
  }

  public static AccountIds fromList(List<AccountId> accountIds) {
    return new AccountIds(accountIds);
  }

  public AccountIds onlyValid() {
    return new AccountIds(
        values.stream()
            .filter(AccountId::isValid)
            .toList()
    );
  }

  public AccountIds onlyExternal() {
    return new AccountIds(
        values.stream()
            .filter(AccountId::isExternal)
            .toList()
    );
  }

  public AccountIds exclude(AccountId toExclude) {
    return new AccountIds(
        values.stream()
            .filter(id -> !id.equals(toExclude))
            .toList()
    );
  }

  public boolean contains(AccountId accountId) {
    return values.contains(accountId);
  }

  public List<String> toStrings() {
    return values.stream()
        .map(AccountId::value)
        .toList();
  }



}
