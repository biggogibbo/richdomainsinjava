package com.roku.richdomains.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customer {
  private final String id;
  private final List<String> accountIds;

  public List<AccountId> getAccountIdsTyped() {
    return accountIds.stream()
        .map(AccountId::of)
        .toList();
  }

  public AccountIds getAccountIdsWrapped() {
    return AccountIds.fromList(getAccountIdsTyped());
  }

  // NEW: Domain logic moved from service
  public AccountIds getExternalAccounts() {
    return getAccountIdsWrapped()
        .onlyValid()
        .onlyExternal();
  }

  // NEW: Domain logic moved from service
  public boolean hasAccount(AccountId accountId) {
    return getAccountIdsWrapped()
        .onlyValid()
        .contains(accountId);
  }

  // NEW: Domain logic moved from service
  public AccountIds getTransferEligibleAccounts(AccountId excludeAccountId) {
    return getAccountIdsWrapped()
        .onlyValid()
        .exclude(excludeAccountId)
        .onlyExternal();
  }

}
