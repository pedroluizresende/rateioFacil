package com.pedroresende.rateiofacil.exceptions;

/**
 * NotFoundBillException.
 */
public class NotFoundBillException extends RuntimeException {

  public NotFoundBillException() {
    super("Conta não encontrada!");
  }
}
