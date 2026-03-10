package com.roku.richdomains.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.roku.richdomains.domain.AccountId;
import com.roku.richdomains.domain.AccountIds;
import com.roku.richdomains.domain.Customer;
import com.roku.richdomains.domain.CustomerId;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerServiceTest {

  @Test
  void getExternalAccounts_filtersInvalidAndBlockedAccounts() {
    CustomerService service = serviceWithAccounts(
        "ACC-12345678",
        "ACC-90000000",
        "BAD-123",
        "",
        null,
        "ACC-87654321"
    );

    AccountIds result = service.getExternalAccounts(CustomerId.of("c1"));

    AccountIds expected = AccountIds.fromList(List.of(AccountId.of("ACC-12345678"),
        AccountId.of("ACC-87654321")));
    assertEquals(expected, result);
  }

  @Test
  void canAccessAccount_returnsFalseForNullBlankOrInvalidFormat() {
    CustomerService service = serviceWithAccounts("ACC-12345678");

    assertFalse(service.canAccessAccount(CustomerId.of("c1"), AccountId.of(null)));
    assertFalse(service.canAccessAccount(CustomerId.of("c1"), AccountId.of("   ")));
    assertFalse(service.canAccessAccount(CustomerId.of("c1"), AccountId.of("ACC-123")));
  }

  @Test
  void canAccessAccount_returnsTrueWhenCustomerOwnsValidAccount() {
    CustomerService service = serviceWithAccounts("ACC-12345678", "ACC-87654321");

    assertTrue(service.canAccessAccount(CustomerId.of("c1"), AccountId.of("ACC-87654321")));
    assertFalse(service.canAccessAccount(CustomerId.of("c1"), AccountId.of("ACC-11111111")));
  }

  @Test
  void getTransferEligibleAccounts_excludesInvalidBlockedAndExcludedId() {
    CustomerService service = serviceWithAccounts(
        "ACC-12345678",
        "ACC-22222222",
        "ACC-90000000",
        "ACC-123",
        "",
        null
    );

    AccountIds result = service.getTransferEligibleAccounts(CustomerId.of("c1"), AccountId.of("ACC-22222222"));
    AccountIds expected = AccountIds.fromList(List.of(AccountId.of("ACC-12345678")));
    assertEquals(expected, result);
  }

  private static CustomerService serviceWithAccounts(String... accountIds) {
    Customer customer = new Customer("c1", Arrays.asList(accountIds));
    CustomerRepository repository = customerId -> customer;
    return new CustomerService(repository);
  }
}
