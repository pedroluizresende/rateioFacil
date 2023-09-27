package com.pedroresende.rateiofacil.enums;

public enum BillStatus {
  OPEN("OPEN"),
  CLOSED("CLOSED"),
  CANCELLED("CANCELLED");

  private final String name;

  BillStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}