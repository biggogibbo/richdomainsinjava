package com.roku.richdomains.service;

import com.roku.richdomains.domain.AccountId;
import com.roku.richdomains.domain.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;

  public List<String> getExternalAccounts(String customerId) {
    Customer customer = repository.findById(customerId);
    List<String> allAccountIds = customer.getAccountIds();

    // Validation logic in service
    List<AccountId> validIds = new ArrayList<>();
    for (String id : allAccountIds) {
      AccountId accountId = AccountId.of(id);
      if (accountId.isValid()) {
        validIds.add(accountId);
      }
    }

    // Business logic in service
    return validIds.stream()
        .filter(id -> !id.isInternal())
        .map(AccountId::value)
        .collect(Collectors.toList());
  }

  public boolean canAccessAccount(String customerId, String accountIdAsString) {
    Customer customer = repository.findById(customerId);
    List<String> accounts = customer.getAccountIds();

    AccountId accountId = AccountId.of(accountIdAsString);

    // Validation scattered everywhere
    if (!accountId.isValid()) {
      return false;
    }

    return accounts.contains(accountId.value());
  }

  public List<String> getTransferEligibleAccounts(String customerId,
                                                  String excludeAccountId) {
    Customer customer = repository.findById(customerId);
    List<String> accounts = customer.getAccountIds();

    // Complex filtering logic in service
    return accounts.stream()
        .map(AccountId::of)
        .filter(AccountId::isValid)
        .filter(id -> !id.value().equals(excludeAccountId))
        .filter(id -> !id.isInternal())
        .map(AccountId::value)
        .collect(Collectors.toList());
  }
}
