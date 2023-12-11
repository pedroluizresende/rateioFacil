package com.pedroresende.rateiofacil.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pedroresende.rateiofacil.controllers.dtos.AuthenticationDto;
import com.pedroresende.rateiofacil.models.entities.User;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Token Service.
 */
@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  /**
   * Método responsável por gerar um token.
   *
   * @param user usuário que efetuou o login e aguarda o token.
   * @return Token no formato de String
   */
  public String generateToken(User user, AuthenticationDto authenticationDto) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create().withIssuer("rateiofacil").withSubject(user.getUsername())
        .withExpiresAt(generateExpirationDate(authenticationDto)).sign(algorithm);
  }

  /**
   * Método para criar token sem AuthenticationDto.
   *
   * @param user usuário que solicitou token.
   * @return retorna token em formato String
   */
  public String generateToken(User user) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create().withIssuer("rateiofacil").withSubject(user.getUsername())
        .withExpiresAt(generateExpirationDate(null)).sign(algorithm);
  }

  private Instant generateExpirationDate(AuthenticationDto authenticationDto) {
    if (authenticationDto != null) {
      System.out.println(authenticationDto.remember());
      return LocalDateTime.now().plusMonths(1).toInstant(ZoneOffset.of("-03:00"));
    }
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.require(algorithm).withIssuer("rateiofacil").build().verify(token).getSubject();
  }
}
