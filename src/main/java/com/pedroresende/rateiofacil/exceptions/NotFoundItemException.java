package com.pedroresende.rateiofacil.exceptions;

/**
 * Classe NotFoundItemException.
 */
public class NotFoundItemException extends RuntimeException {

  public NotFoundItemException() {
    super("Item não encontrado!");
  }
}
