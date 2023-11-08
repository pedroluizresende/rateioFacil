package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.User;

/**
 * Classe UpdateUserDto.
 *
 * @param name     novo nome do usuário.
 * @param username novo username do usuário.
 * @param email    novo email do usuário.
 * @param role     nova role do usuário.
 */
public record UpdateUserDto(String name, String username, String email, String role) {

  public User toEntity() {
    return new User(null, name, email, username, null, role, null);
  }
}
