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
    List<String> validIds = new ArrayList<>();
    for (String id : allAccountIds) {
      if (id != null && !id.isBlank() && id.matches("ACC-\\d{8}")) {
        validIds.add(id);
      }
    }

    // Business logic in service
    return validIds.stream()
        // converts to domain object
        .map(AccountId::of)
        .filter(AccountId::isExternal)
        // convert back to string
        .map(AccountId::value)
        .collect(Collectors.toList());
  }

  public boolean canAccessAccount(String customerId, String accountId) {
    Customer customer = repository.findById(customerId);
    List<String> accounts = customer.getAccountIds();

    // Validation scattered everywhere
    if (accountId == null || accountId.isBlank()) {
      return false;
    }
    if (!accountId.matches("ACC-\\d{8}")) {
      return false;
    }

    return accounts.contains(accountId);
  }

  public List<String> getTransferEligibleAccounts(String customerId,
                                                  String excludeAccountId) {
    Customer customer = repository.findById(customerId);
    List<String> accounts = customer.getAccountIds();

    // Complex filtering logic in service
    return accounts.stream()
        .filter(id -> id != null && !id.isBlank())
        .filter(id -> id.matches("ACC-\\d{8}"))
        .filter(id -> !id.equals(excludeAccountId))
        // converts to domain object
        .map(AccountId::of)
        .filter(AccountId::isExternal)
        // convert back to string
        .map(AccountId::value)
        .collect(Collectors.toList());
  }
}
