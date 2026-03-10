package com.roku.richdomains.service;

import com.roku.richdomains.domain.AccountId;
import com.roku.richdomains.domain.AccountIds;
import com.roku.richdomains.domain.Customer;
import com.roku.richdomains.domain.CustomerId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;

  public AccountIds getExternalAccounts(CustomerId customerId) {
    Customer customer = repository.findById(customerId);
    return customer.getExternalAccounts();
  }

  public boolean canAccessAccount(CustomerId customerId, AccountId accountId) {
    if (!accountId.isValid()) {
      return false;
    }
    Customer customer = repository.findById(customerId);
    return customer.hasAccount(accountId);
  }

  public AccountIds getTransferEligibleAccounts(CustomerId customerId, AccountId excludeAccountId) {
    Customer customer = repository.findById(customerId);
    return customer.getTransferEligibleAccounts(excludeAccountId);
  }
}
