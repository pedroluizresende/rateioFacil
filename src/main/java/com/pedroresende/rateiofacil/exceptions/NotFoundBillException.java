package com.pedroresende.rateiofacil.exceptions;

public class NotFoundBillException extends RuntimeException {

  public NotFoundBillException() {
    super("Conta não encontrada!");
  }
}
