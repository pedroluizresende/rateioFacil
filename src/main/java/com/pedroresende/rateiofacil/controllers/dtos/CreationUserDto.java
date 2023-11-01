package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.User;

/**
 * CreationUserDto, informações de um novo usuário.
 *
 * @param name     nome do usuário.
 * @param email    email do usuário.
 * @param username username do usuário.
 * @param password senha do usuário.
 * @param role     permissão do usuário.
 */
public record CreationUserDto(String name, String email, String username, String password,
                              String role) {

  public User toEntity() {
    return new User(null, name, email, username, password, role, null);
  }
}
