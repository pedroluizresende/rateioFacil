package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.enums.UserStatus;

/**
 * UserDto, Formato de retorno de informações de usuário.
 *
 * @param id       id do usuário no banco.
 * @param name     nomde do usuário.
 * @param email    email do usuário.
 * @param username username do usuário.
 */
public record UserDto(Long id, String name, String email, String username, UserStatus status) {

}
