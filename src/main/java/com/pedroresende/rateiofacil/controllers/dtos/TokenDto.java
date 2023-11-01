package com.pedroresende.rateiofacil.controllers.dtos;

/**
 * TokenDto, formato de retorno de um token.
 *
 * @param token token à ser retornado.
 */
public record TokenDto(String token, UserDto user) {

}
