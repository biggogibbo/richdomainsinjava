package com.roku.richdomains.domain;

import java.util.List;

public class Customer {
  private final String id;
  private final List<String> accountIds;

  public Customer(String id, List<String> accountIds) {
    this.id = id;
    this.accountIds = accountIds;
  }

  public String getId() {
    return id;
  }

  public List<String> getAccountIds() {
    return accountIds;
  }
}
