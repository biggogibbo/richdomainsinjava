package com.roku.richdomains.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

  @Test
  void fromRawAccountIds_filtersInvalidIds() {
    Customer customer = Customer.fromRawAccountIds("c1", Arrays.asList(
        "ACC-12345678",
        "BAD-123",
        "",
        null,
        "ACC-87654321"
    ));

    assertEquals(2, customer.getAccounts().size());
    assertEquals("ACC-12345678", customer.getAccounts().get(0).value());
    assertEquals("ACC-87654321", customer.getAccounts().get(1).value());
  }

  @Test
  void getExternalAccounts_excludesBlockedAccounts() {
    Customer customer = Customer.fromRawAccountIds("c1", Arrays.asList(
        "ACC-12345678",
        "ACC-90000000",
        "ACC-87654321"
    ));

    List<AccountId> external = customer.getExternalAccounts();

    assertEquals(2, external.size());
    assertEquals("ACC-12345678", external.get(0).value());
    assertEquals("ACC-87654321", external.get(1).value());
  }

  @Test
  void canAccessAccount_returnsTrueForOwnedAccount() {
    Customer customer = Customer.fromRawAccountIds("c1", List.of("ACC-12345678"));

    assertTrue(customer.canAccessAccount(AccountId.of("ACC-12345678")));
  }

  @Test
  void canAccessAccount_returnsFalseForUnownedAccount() {
    Customer customer = Customer.fromRawAccountIds("c1", List.of("ACC-12345678"));

    assertFalse(customer.canAccessAccount(AccountId.of("ACC-99999999")));
  }

  @Test
  void canAccessAccount_returnsFalseForNull() {
    Customer customer = Customer.fromRawAccountIds("c1", List.of("ACC-12345678"));

    assertFalse(customer.canAccessAccount(null));
  }

  @Test
  void getTransferEligibleAccounts_excludesBlockedAndSpecifiedAccount() {
    Customer customer = Customer.fromRawAccountIds("c1", Arrays.asList(
        "ACC-12345678",
        "ACC-22222222",
        "ACC-90000000"
    ));

    List<AccountId> eligible = customer.getTransferEligibleAccounts(
        AccountId.of("ACC-22222222"));

    assertEquals(1, eligible.size());
    assertEquals("ACC-12345678", eligible.get(0).value());
  }

  @Test
  void getTransferEligibleAccounts_handlesNullExclude() {
    Customer customer = Customer.fromRawAccountIds("c1", Arrays.asList(
        "ACC-12345678",
        "ACC-90000000"
    ));

    List<AccountId> eligible = customer.getTransferEligibleAccounts(null);

    assertEquals(1, eligible.size());
    assertEquals("ACC-12345678", eligible.get(0).value());
  }

  @Test
  void accounts_areImmutable() {
    Customer customer = Customer.fromRawAccountIds("c1", List.of("ACC-12345678"));

    assertThrows(UnsupportedOperationException.class, () ->
        customer.getAccounts().add(AccountId.of("ACC-99999999")));
  }
}
