package com.roku.richdomains.service;

import com.roku.richdomains.domain.AccountId;
import com.roku.richdomains.domain.AccountIds;
import com.roku.richdomains.domain.Customer;
import com.roku.richdomains.domain.CustomerId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;

  // accepts CustomerId domain object
  public AccountIds getExternalAccounts(CustomerId customerId) {
    Customer customer = repository.findById(customerId);
    return customer.getAccountIdsWrapped()
        .onlyValid()
        .onlyExternal();
  }

  // accepts CustomerId domain object
  public boolean canAccessAccount(CustomerId customerId, AccountId accountId) {
    Customer customer = repository.findById(customerId);

    if (!accountId.isValid()) {
      return false;
    }

    return customer.getAccountIdsWrapped()
        .onlyValid()
        .contains(accountId);
  }

  // accepts CustomerId domain object
  public AccountIds getTransferEligibleAccounts(CustomerId customerId, AccountId excludeAccountId) {
    Customer customer = repository.findById(customerId);

    return customer.getAccountIdsWrapped()
        .onlyValid()
        .exclude(excludeAccountId)
        .onlyExternal();
  }
}
