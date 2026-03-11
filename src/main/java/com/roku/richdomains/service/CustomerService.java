package com.roku.richdomains.service;

import com.roku.richdomains.domain.AccountId;
import com.roku.richdomains.domain.Customer;

import java.util.List;

public class CustomerService {

  private final CustomerRepository repository;

  public CustomerService(CustomerRepository repository) {
    this.repository = repository;
  }

  public List<String> getExternalAccounts(String customerId) {
    Customer customer = repository.findById(customerId);
    return customer.getExternalAccounts().stream()
        .map(AccountId::value)
        .toList();
  }

  public boolean canAccessAccount(String customerId, String accountId) {
    Customer customer = repository.findById(customerId);
    AccountId account = AccountId.ofNullable(accountId);
    return customer.canAccessAccount(account);
  }

  public List<String> getTransferEligibleAccounts(String customerId, String excludeAccountId) {
    Customer customer = repository.findById(customerId);
    AccountId excludeAccount = AccountId.ofNullable(excludeAccountId);
    return customer.getTransferEligibleAccounts(excludeAccount).stream()
        .map(AccountId::value)
        .toList();
  }
}
