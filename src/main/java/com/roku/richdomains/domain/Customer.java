package com.roku.richdomains.domain;

import java.util.List;
import java.util.Objects;

public class Customer {
  private final String id;
  private final List<AccountId> accounts;

  public Customer(String id, List<AccountId> accounts) {
    this.id = Objects.requireNonNull(id, "Customer ID cannot be null");
    this.accounts = List.copyOf(accounts);
  }

  public static Customer fromRawAccountIds(String id, List<String> rawAccountIds) {
    List<AccountId> validAccounts = rawAccountIds.stream()
        .map(AccountId::ofNullable)
        .filter(Objects::nonNull)
        .toList();
    return new Customer(id, validAccounts);
  }

  public String getId() {
    return id;
  }

  public List<AccountId> getAccounts() {
    return accounts;
  }

  public List<AccountId> getExternalAccounts() {
    return accounts.stream()
        .filter(AccountId::isExternal)
        .toList();
  }

  public boolean canAccessAccount(AccountId accountId) {
    if (accountId == null) {
      return false;
    }
    return accounts.contains(accountId);
  }

  public List<AccountId> getTransferEligibleAccounts(AccountId excludeAccount) {
    return accounts.stream()
        .filter(AccountId::isExternal)
        .filter(account -> !account.equals(excludeAccount))
        .toList();
  }
}
