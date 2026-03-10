package com.roku.richdomains.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountId Tests")
class AccountIdTest {

  @Nested
  @DisplayName("Valid Account IDs")
  class ValidAccountIds {

    @Test
    @DisplayName("should create valid account ID with correct format")
    void shouldCreateValidAccountId() {
      AccountId accountId = AccountId.of("ACC-12345678");

      assertTrue(accountId.isValid());
      assertEquals("ACC-12345678", accountId.get().orElseThrow());
      assertEquals("", accountId.reason());
    }

    @Test
    @DisplayName("should trim whitespace from account ID")
    void shouldTrimWhitespace() {
      AccountId accountId = AccountId.of("  ACC-87654321  ");

      assertTrue(accountId.isValid());
      assertEquals("ACC-87654321", accountId.get().orElseThrow());
    }

    @Test
    @DisplayName("should identify external account IDs (not starting with ACC-9)")
    void shouldIdentifyExternalAccountId() {
      AccountId accountId = AccountId.of("ACC-12345678");

      assertTrue(accountId.isExternal());
    }

    @Test
    @DisplayName("should identify internal account IDs (starting with ACC-9)")
    void shouldIdentifyInternalAccountId() {
      AccountId accountId = AccountId.of("ACC-98765432");

      assertFalse(accountId.isExternal());
    }
  }

  @Nested
  @DisplayName("Invalid Account IDs")
  class InvalidAccountIds {

    @Test
    @DisplayName("should reject null account ID")
    void shouldRejectNull() {
      AccountId accountId = AccountId.of(null);

      assertFalse(accountId.isValid());
      assertTrue(accountId.get().isEmpty());
      assertEquals("Account ID cannot be blank", accountId.reason());
    }

    @Test
    @DisplayName("should reject blank account ID")
    void shouldRejectBlank() {
      AccountId accountId = AccountId.of("   ");

      assertFalse(accountId.isValid());
      assertEquals("Account ID cannot be blank", accountId.reason());
    }

    @Test
    @DisplayName("should reject empty account ID")
    void shouldRejectEmpty() {
      AccountId accountId = AccountId.of("");

      assertFalse(accountId.isValid());
      assertEquals("Account ID cannot be blank", accountId.reason());
    }

    @Test
    @DisplayName("should reject account ID without ACC- prefix")
    void shouldRejectMissingPrefix() {
      AccountId accountId = AccountId.of("12345678");

      assertFalse(accountId.isValid());
      assertTrue(accountId.reason().contains("Invalid account ID format"));
    }

    @Test
    @DisplayName("should reject account ID with wrong number of digits")
    void shouldRejectWrongDigitCount() {
      AccountId accountId = AccountId.of("ACC-1234567");

      assertFalse(accountId.isValid());
      assertTrue(accountId.reason().contains("Invalid account ID format"));
    }

    @Test
    @DisplayName("should reject account ID with non-digit characters")
    void shouldRejectNonDigits() {
      AccountId accountId = AccountId.of("ACC-1234567A");

      assertFalse(accountId.isValid());
      assertTrue(accountId.reason().contains("Invalid account ID format"));
    }

    @Test
    @DisplayName("should reject account ID with special characters")
    void shouldRejectSpecialCharacters() {
      AccountId accountId = AccountId.of("ACC-123#5678");

      assertFalse(accountId.isValid());
      assertTrue(accountId.reason().contains("Invalid account ID format"));
    }
  }

  @Nested
  @DisplayName("isExternal() edge cases")
  class IsExternalEdgeCases {

    @Test
    @DisplayName("should return false for invalid account IDs")
    void shouldReturnFalseForInvalidId() {
      AccountId accountId = AccountId.of("INVALID");

      assertFalse(accountId.isExternal());
    }

    @Test
    @DisplayName("should identify boundary case ACC-90000000 as internal")
    void shouldIdentifyBoundaryCaseAsInternal() {
      AccountId accountId = AccountId.of("ACC-90000000");

      assertFalse(accountId.isExternal());
    }

    @Test
    @DisplayName("should identify boundary case ACC-89999999 as external")
    void shouldIdentifyBoundaryCaseAsExternal() {
      AccountId accountId = AccountId.of("ACC-89999999");

      assertTrue(accountId.isExternal());
    }
  }
}

