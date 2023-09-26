package com.pedroresende.rateiofacil.exceptions;

/**
 * Exceção para usuário não encontrado.
 */
public class NotFoundUserException extends RuntimeException {

  public NotFoundUserException() {
    super("Usuário não encontrado!");
  }
}
