package com.roku.richdomains.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customer {
  private final String id;
  private final List<String> accountIds;

  // NEW: Return typed list
  public List<AccountId> getAccountIdsTyped() {
    return accountIds.stream()
        .map(AccountId::of)
        .toList();
  }

}
