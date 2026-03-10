package com.roku.richdomains.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customer {
  private final CustomerId id;
  private final AccountIds accountIds;


  // Domain logic - cleaner now
  public AccountIds getExternalAccounts() {
    return accountIds
        .onlyValid()
        .onlyExternal();
  }

  // Domain logic - cleaner now
  public boolean hasAccount(AccountId accountId) {
    return accountIds
        .onlyValid()
        .contains(accountId);
  }

  // Domain logic - cleaner now
  public AccountIds getTransferEligibleAccounts(AccountId excludeAccountId) {
    return accountIds
        .onlyValid()
        .exclude(excludeAccountId)
        .onlyExternal();
  }

}
