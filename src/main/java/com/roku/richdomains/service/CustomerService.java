package com.roku.richdomains.service;

import com.roku.richdomains.domain.AccountId;
import com.roku.richdomains.domain.Customer;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;

  public List<String> getExternalAccounts(String customerId) {
    Customer customer = repository.findById(customerId);
    // NEW: use logic in AccountIds
    return customer.getAccountIdsWrapped()
        .onlyValid()
        .onlyExternal()
        .toStrings();
  }

  public boolean canAccessAccount(String customerId, String accountIdAsString) {
    Customer customer = repository.findById(customerId);
    AccountId accountId = AccountId.of(accountIdAsString);

    if (!accountId.isValid()) {
      return false;
    }

    return customer.getAccountIdsWrapped()
        .onlyValid()
        .contains(accountId);
  }

  public List<String> getTransferEligibleAccounts(String customerId,
                                                  String excludeAccountId) {
    Customer customer = repository.findById(customerId);
    AccountId excludeId = AccountId.of(excludeAccountId);

    return customer.getAccountIdsWrapped()
        .onlyValid()
        .exclude(excludeId)
        .onlyExternal()
        .toStrings();
  }
}
