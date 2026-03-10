package com.roku.richdomains.service;

import com.roku.richdomains.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    List<String> result = service.getExternalAccounts("c1");

    assertIterableEquals(List.of("ACC-12345678", "ACC-87654321"), result);
  }

  @Test
  void canAccessAccount_returnsFalseForNullBlankOrInvalidFormat() {
    CustomerService service = serviceWithAccounts("ACC-12345678");

    assertFalse(service.canAccessAccount("c1", null));
    assertFalse(service.canAccessAccount("c1", "   "));
    assertFalse(service.canAccessAccount("c1", "ACC-123"));
  }

  @Test
  void canAccessAccount_returnsTrueWhenCustomerOwnsValidAccount() {
    CustomerService service = serviceWithAccounts("ACC-12345678", "ACC-87654321");

    assertTrue(service.canAccessAccount("c1", "ACC-87654321"));
    assertFalse(service.canAccessAccount("c1", "ACC-11111111"));
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

    List<String> result = service.getTransferEligibleAccounts("c1", "ACC-22222222");

    assertEquals(List.of("ACC-12345678"), result);
  }

  private static CustomerService serviceWithAccounts(String... accountIds) {
    Customer customer = new Customer("c1", Arrays.asList(accountIds));
    CustomerRepository repository = customerId -> customer;
    return new CustomerService(repository);
  }
}
