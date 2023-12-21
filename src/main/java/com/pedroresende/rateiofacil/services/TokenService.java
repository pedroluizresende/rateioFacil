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

  private static final String ISSUER = "rateiofacil";

  /**
   * Método responsável por gerar um token.
   *
   * @param user              usuário que efetuou o login e aguarda o token.
   * @param authenticationDto dados de autenticação.
   * @return Token no formato de String
   */
  public String generateToken(User user, AuthenticationDto authenticationDto) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(user.getUsername())
        .withExpiresAt(generateExpirationDate(authenticationDto))
        .sign(algorithm);
  }

  /**
   * Método para criar token sem AuthenticationDto.
   *
   * @param user usuário que solicitou token.
   * @return retorna token em formato String
   */
  public String generateToken(User user) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(user.getUsername())
        .withExpiresAt(generateExpirationDate(null))
        .sign(algorithm);
  }

  private Instant generateExpirationDate(AuthenticationDto authenticationDto) {
    if (authenticationDto != null && authenticationDto.remember()) {
      return LocalDateTime.now().plusMonths(1).toInstant(ZoneOffset.of("-03:00"));
    }
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

  /**
   * Método responsável por validar um token.
   *
   * @param token token a ser validado.
   * @return usuário associado ao token.
   */
  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build()
        .verify(token)
        .getSubject();
  }
}
