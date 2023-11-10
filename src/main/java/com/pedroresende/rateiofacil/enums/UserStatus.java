package com.pedroresende.rateiofacil.enums;

/**
 * Enum UserStatus.
 */
public enum UserStatus {
  CONFIRMED("CONFIRMED"), NOT_CONFIRMED("NOT_CONFIRMED");
  private final String name;

  UserStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
