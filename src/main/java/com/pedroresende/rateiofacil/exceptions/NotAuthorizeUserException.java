package com.pedroresende.rateiofacil.exceptions;

/**
 * Classe NotAuthorizeUserException.
 */
public class NotAuthorizeUserException extends RuntimeException {

  public NotAuthorizeUserException() {
    super("Usuário não autorizado!");
  }
}
