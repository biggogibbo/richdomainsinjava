package com.roku.richdomains.domain;

import java.util.Optional;

public sealed interface AccountId {

  Optional<String> get();
  String reason();

  default boolean isValid() {
    return get().isPresent();
  }

  static AccountId of(String value) {
    if (value == null || value.isBlank()) {
      return new Invalid("Account ID cannot be blank");
    }
    String trimmed = value.trim();
    if (!trimmed.matches("ACC-\\d{8}")) {
      return new Invalid("Invalid account ID format: " + trimmed);
    }
    return new Valid(trimmed);
  }

  default boolean isExternal() {
    return get()
        .map(v -> !v.startsWith("ACC-9"))
        .orElse(false);
  }

  record Valid(String value) implements AccountId {
    @Override
    public Optional<String> get() {
      return Optional.of(value);
    }

    @Override
    public String reason() {
      return "";
    }
  }

  record Invalid(String reason) implements AccountId {
    @Override
    public Optional<String> get() {
      return Optional.empty();
    }
  }

}
