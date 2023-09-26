package com.pedroresende.rateiofacil.controllers.dtos;

/**
 * AuthenticationDto, formato de entrada para efetuar login.
 *
 * @param username username do usuario.
 * @param password senha do usuário.
 */
public record AuthenticationDto(String username, String password) {

}
