package com.pedroresende.rateiofacil.enums;

/**
 * enum BillStatus.
 */
public enum BillStatus {
  OPEN("OPEN"),
  CLOSED("CLOSED");
  private final String name;

  BillStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}