package com.pedroresende.rateiofacil.controllers;

import com.pedroresende.rateiofacil.controllers.dtos.ResponseDto;
import com.pedroresende.rateiofacil.exceptions.BadRequestException;
import com.pedroresende.rateiofacil.exceptions.NotAuthorizeUserException;
import com.pedroresende.rateiofacil.exceptions.NotFoundBillException;
import com.pedroresende.rateiofacil.exceptions.NotFoundItemException;
import com.pedroresende.rateiofacil.exceptions.NotFoundUserException;
import javax.naming.AuthenticationException;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ControllerAdvice.
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

  /**
   * Captura e trata os error de usuário não encontrado.
   */
  @ExceptionHandler({NotFoundUserException.class})
  public ResponseEntity<ResponseDto<String>> handleNotFoundUserExcepetion(NotFoundUserException e) {
    ResponseDto<String> responseDto = new ResponseDto<>(e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
  }

  /**
   * Captura e trata os error de conta não encontrado.
   */
  @ExceptionHandler({NotFoundBillException.class})
  public ResponseEntity<ResponseDto<String>> handleNotFoundBillExcepetion(NotFoundBillException e) {
    ResponseDto<String> responseDto = new ResponseDto<>(e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
  }

  /**
   * Captura e trata os erros de usuário não autorizado.
   */
  @ExceptionHandler({NotAuthorizeUserException.class})
  public ResponseEntity<ResponseDto<String>> handleNotAuthorizeUserException(
      NotAuthorizeUserException e) {
    ResponseDto<String> responseDto = new ResponseDto<>(e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
  }

  /**
   * Captura e trata os erros de item não encontrado.
   */
  @ExceptionHandler({NotFoundItemException.class})
  public ResponseEntity<ResponseDto<String>> handleNotFoundItemExcepetion(NotFoundItemException e) {
    ResponseDto<String> responseDto = new ResponseDto<>(e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
  }

  /**
   * Captura e trata erros de badrequest.
   *
   * @param e exceção do tipo BadRequest.
   * @return status 400 e mensagem de erro
   */
  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<ResponseDto<String>> handleBadRequestException(BadRequestException e) {
    ResponseDto<String> responseDto = new ResponseDto<>(e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
  }

  /**
   * Captura e trata erros de Atuthenticação.
   *
   * @param e exceção do tipo AuthenticationExcepetion.
   * @return retorna status 400 e mensagem de erro
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ResponseDto<String>> handleAuthenticationException(
      AuthenticationException e) {
    ResponseDto<String> responseDto = new ResponseDto<>(e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
  }
}
