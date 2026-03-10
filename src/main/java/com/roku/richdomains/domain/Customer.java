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

  // NEW: Return wrapper
  public AccountIds getAccountIdsWrapped() {
    return AccountIds.fromList(getAccountIdsTyped());
  }

}
