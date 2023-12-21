package com.pedroresende.rateiofacil.listeners;

import com.pedroresende.rateiofacil.exceptions.BadRequestException;
import com.pedroresende.rateiofacil.models.entities.User;
import com.pedroresende.rateiofacil.services.EmailService;
import com.pedroresende.rateiofacil.services.TokenService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * UserListener.
 */
@Component
public class UserListener {

  private final TokenService tokenService;
  private final EmailService emailService;

  @Value("${frontend.url}")
  private String frontUrl;

  public UserListener(TokenService tokenService, EmailService emailService) {
    this.tokenService = tokenService;
    this.emailService = emailService;
  }

  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)?(\\.com|\\.br)$";
  private static final String NAME_LENGTH_ERROR = "Nome deve ter no mínimo 2 caracteres";
  private static final String USERNAME_LENGTH_ERROR = "Username deve ter no mínimo 6 caracteres";
  private static final String EMAIL_REQUIRED_ERROR = "Email é obrigatório!";
  private static final String NAME_REQUIRED_ERROR = "Nome é obrigatório!";
  private static final String USERNAME_REQUIRED_ERROR = "Username é obrigatório!";
  private static final String ROLE_REQUIRED_ERROR = "Role é obrigatório!";
  private static final String CONFIRMATION_SUBJECT = "Confirme seu cadastro(rateiofacil)";

  @PrePersist
  public void validateCreateUser(User user) {
    validateUser(user);
  }

  @PreUpdate
  public void validateUpdateUser(User user) {
    validateUser(user);
  }

  /**
   * Método chamado após inserção de usuário no banco e envia email de confirmação.
   *
   * @param user usuário inserido.
   */
  @PostPersist
  public void sendEmailConfirm(User user) {
    String token = tokenService.generateToken(user);

    if (!"admin".equals(user.getRole())) {
      String confirmationUrl = buildConfirmationUrl(token);

      Map<String, Object> emailVariables = new HashMap<>();
      emailVariables.put("username", user.getUsername());
      emailVariables.put("confirmationUrl", confirmationUrl);

      emailService.sendHtmlEmailFromFile(user.getEmail(), CONFIRMATION_SUBJECT, emailVariables);
    }
  }

  private String buildConfirmationUrl(String token) {
    return frontUrl + "/confirmacao?token=" + token;
  }

  private void validateEmail(String email) {
    if (email == null) {
      throw new BadRequestException(EMAIL_REQUIRED_ERROR);
    }

    Pattern pattern = Pattern.compile(EMAIL_REGEX);
    Matcher matcher = pattern.matcher(email);

    if (!matcher.matches()) {
      throw new BadRequestException("Email no formato errado!");
    }
  }

  private void validateName(String name) {
    if (name == null) {
      throw new BadRequestException(NAME_REQUIRED_ERROR);
    }

    if (name.length() < 2) {
      throw new BadRequestException(NAME_LENGTH_ERROR);
    }
  }

  private void validateUsername(String username) {
    if (username == null) {
      throw new BadRequestException(USERNAME_REQUIRED_ERROR);
    }

    if (username.length() < 5) {
      throw new BadRequestException(USERNAME_LENGTH_ERROR);
    }
  }

  private void validateRole(String role) {
    if (role == null) {
      throw new BadRequestException(ROLE_REQUIRED_ERROR);
    }
  }

  private void validateUser(User user) {
    validateEmail(user.getEmail());
    validateName(user.getName());
    validateUsername(user.getUsername());
    validateRole(user.getRole());
  }
}
