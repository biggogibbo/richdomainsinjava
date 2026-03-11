package com.roku.richdomains.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountIdTest {

  @Test
  void of_createsValidAccountId() {
    AccountId id = AccountId.of("ACC-12345678");
    assertEquals("ACC-12345678", id.value());
  }

  @Test
  void of_throwsForNull() {
    assertThrows(NullPointerException.class, () -> AccountId.of(null));
  }

  @Test
  void of_throwsForBlank() {
    assertThrows(IllegalArgumentException.class, () -> AccountId.of(""));
    assertThrows(IllegalArgumentException.class, () -> AccountId.of("   "));
  }

  @Test
  void of_throwsForInvalidFormat() {
    assertThrows(IllegalArgumentException.class, () -> AccountId.of("ACC-123"));
    assertThrows(IllegalArgumentException.class, () -> AccountId.of("BAD-12345678"));
    assertThrows(IllegalArgumentException.class, () -> AccountId.of("ACC-1234567"));
    assertThrows(IllegalArgumentException.class, () -> AccountId.of("ACC-123456789"));
  }

  @Test
  void ofNullable_returnsNullForInvalidInput() {
    assertNull(AccountId.ofNullable(null));
    assertNull(AccountId.ofNullable(""));
    assertNull(AccountId.ofNullable("   "));
    assertNull(AccountId.ofNullable("BAD-123"));
  }

  @Test
  void ofNullable_returnsAccountIdForValidInput() {
    AccountId id = AccountId.ofNullable("ACC-12345678");
    assertNotNull(id);
    assertEquals("ACC-12345678", id.value());
  }

  @Test
  void isBlocked_returnsTrueForBlockedAccounts() {
    assertTrue(AccountId.of("ACC-90000000").isBlocked());
    assertTrue(AccountId.of("ACC-99999999").isBlocked());
  }

  @Test
  void isBlocked_returnsFalseForNonBlockedAccounts() {
    assertFalse(AccountId.of("ACC-12345678").isBlocked());
    assertFalse(AccountId.of("ACC-00000000").isBlocked());
    assertFalse(AccountId.of("ACC-89999999").isBlocked());
  }

  @Test
  void isExternal_isOppositeOfIsBlocked() {
    AccountId blocked = AccountId.of("ACC-90000000");
    AccountId external = AccountId.of("ACC-12345678");

    assertFalse(blocked.isExternal());
    assertTrue(external.isExternal());
  }

  @Test
  void equals_comparesValues() {
    AccountId id1 = AccountId.of("ACC-12345678");
    AccountId id2 = AccountId.of("ACC-12345678");
    AccountId id3 = AccountId.of("ACC-87654321");

    assertEquals(id1, id2);
    assertNotEquals(id1, id3);
  }

  @Test
  void hashCode_consistentWithEquals() {
    AccountId id1 = AccountId.of("ACC-12345678");
    AccountId id2 = AccountId.of("ACC-12345678");

    assertEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void toString_returnsValue() {
    AccountId id = AccountId.of("ACC-12345678");
    assertEquals("ACC-12345678", id.toString());
  }
}
