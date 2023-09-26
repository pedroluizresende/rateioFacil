package com.pedroresende.rateiofacil.controllers;

import com.pedroresende.rateiofacil.controllers.dtos.AuthenticationDto;
import com.pedroresende.rateiofacil.controllers.dtos.ResponseDto;
import com.pedroresende.rateiofacil.controllers.dtos.TokenDto;
import com.pedroresende.rateiofacil.models.entities.User;
import com.pedroresende.rateiofacil.services.TokenService;
import com.pedroresende.rateiofacil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Camada de controller da rota /auth.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  private final TokenService tokenService;

  /**
   * Método construtor.
   */
  @Autowired
  public AuthController(AuthenticationManager authenticationManager, UserService userService,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.tokenService = tokenService;
  }

  /**
   * Mapeamento da rota POST /auth/login.
   */
  @PostMapping("/login")
  public ResponseEntity<ResponseDto<TokenDto>> login(
      @RequestBody AuthenticationDto authenticationDto) {
    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(authenticationDto.username(),
            authenticationDto.password());
    Authentication auth = authenticationManager.authenticate(usernamePassword);
    User user = (User) auth.getPrincipal();

    TokenDto tokenDto = new TokenDto(tokenService.generateToken(user));

    ResponseDto<TokenDto> response = new ResponseDto<>("Pessoa autenticada com sucesso!",
        tokenDto);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
