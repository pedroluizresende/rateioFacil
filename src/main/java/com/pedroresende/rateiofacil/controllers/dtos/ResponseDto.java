package com.pedroresende.rateiofacil.controllers.dtos;

/**
 * ResponseDto, formato de resposta da API.
 *
 * @param message mensagem personalizada.
 * @param data    dado enviado no corpo da resposta.
 * @param <T>     tipo do dado enviado.
 */
public record ResponseDto<T>(String message, T data) {

}
