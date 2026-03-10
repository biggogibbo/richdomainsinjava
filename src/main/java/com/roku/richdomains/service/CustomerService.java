package com.roku.richdomains.service;

import com.roku.richdomains.domain.AccountId;
import com.roku.richdomains.domain.AccountIds;
import com.roku.richdomains.domain.Customer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;

  public AccountIds getExternalAccounts(String customerId) {
    Customer customer = repository.findById(customerId);
    // NEW: remove toString() and return wrapper instead of List<String>
    return customer.getAccountIdsWrapped()
        .onlyValid()
        .onlyExternal();
  }

  public boolean canAccessAccount(String customerId, AccountId accountId) {
    Customer customer = repository.findById(customerId);

    if (!accountId.isValid()) {
      return false;
    }

    return customer.getAccountIdsWrapped()
        .onlyValid()
        .contains(accountId);
  }

  public AccountIds getTransferEligibleAccounts(String customerId,
                                                  AccountId excludeAccountId) {
    Customer customer = repository.findById(customerId);

    // NEW: remove toString() and return wrapper instead of List<String>
    return customer.getAccountIdsWrapped()
        .onlyValid()
        .exclude(excludeAccountId)
        .onlyExternal();
  }
}
