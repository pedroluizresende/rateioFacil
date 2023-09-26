package com.pedroresende.rateiofacil.controllers;

import com.pedroresende.rateiofacil.controllers.dtos.ResponseDto;
import com.pedroresende.rateiofacil.exceptions.NotFoundUserException;
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
}
