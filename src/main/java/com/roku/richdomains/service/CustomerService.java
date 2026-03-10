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
    // NEW: Get typed account IDs from the domain to stop conversion to domain objects in service
    List<AccountId> allAccountIds = customer.getAccountIdsTyped();

    // Validation logic in service
    List<AccountId> validIds = new ArrayList<>();
    for (AccountId accountId : allAccountIds) {
      if (accountId.isValid()) {
        validIds.add(accountId);
      }
    }

    // Business logic in service
    return validIds.stream()
        .filter(AccountId::isExternal)
        // convert back to string
        .map(AccountId::value)
        .collect(Collectors.toList());
  }

  public boolean canAccessAccount(String customerId, String accountIdAsString) {
    Customer customer = repository.findById(customerId);
    // NEW: Get typed account IDs from the domain
    List<AccountId> accounts = customer.getAccountIdsTyped();
    // still need to convert to domain here since we are accepting a string input
    AccountId accountId = AccountId.of(accountIdAsString);
    if (!accountId.isValid()) {
      return false;
    }
    // no longer need to use .value() for comparison since we are working with domain objects
    return accounts.contains(accountId);
  }

  public List<String> getTransferEligibleAccounts(String customerId,
                                                  String excludeAccountId) {
    Customer customer = repository.findById(customerId);
    List<AccountId> accounts = customer.getAccountIdsTyped();

    // Complex filtering logic in service
    return accounts.stream()
        // remove convert to domain object
        .filter(AccountId::isValid)
        .filter(id -> !id.equals(AccountId.of(excludeAccountId)))
        .filter(AccountId::isExternal)
        // convert back to string
        .map(AccountId::value)
        .collect(Collectors.toList());
  }
}
