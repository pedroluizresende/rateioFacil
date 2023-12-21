package com.pedroresende.rateiofacil.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

/**
 * EmailService.
 */
@Service
public class EmailService {

  @Value("${email.username}")
  private String username;

  @Value("${email.password}")
  private String password;

  public EmailService() {
  }

  /**
   * Método responsável por enviar um email.
   *
   * @param to      email destinatário.
   * @param subject assunto do email.
   * @param message mensagem do email.
   */
  public void sendEmail(String to, String subject, String message) {
    Properties props = new Properties();
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", 587);

    Session session = Session.getInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    try {
      Message mimeMessage = new MimeMessage(session);
      mimeMessage.setFrom(new InternetAddress(username));
      mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      mimeMessage.setSubject(subject);
      mimeMessage.setText(message);

      Transport.send(mimeMessage);
      System.out.println("Email enviado com sucesso!");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Método responsável por enviar um email HTML a partir de um arquivo.
   *
   * @param to        email destinatário.
   * @param subject   assunto do email.
   * @param variables variáveis de modelo para substituição no HTML.
   */
  public void sendHtmlEmailFromFile(String to, String subject, Map<String, Object> variables) {
    try {
      String htmlContent = loadHtmlContentFromFile("templates/emails/confirmation.html");

      for (Map.Entry<String, Object> entry : variables.entrySet()) {
        String variable = "${" + entry.getKey() + "}";
        String value = entry.getValue().toString();
        htmlContent = htmlContent.replace(variable, value);
      }

      Properties props = new Properties();
      props.put("mail.smtp.auth", true);
      props.put("mail.smtp.starttls.enable", true);
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", 587);

      Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      });

      Message mimeMessage = new MimeMessage(session);
      mimeMessage.setFrom(new InternetAddress(username));
      mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      mimeMessage.setSubject(subject);
      mimeMessage.setContent(htmlContent, "text/html; charset=utf-8");

      Transport.send(mimeMessage);
      System.out.println("E-mail HTML enviado com sucesso!");

    } catch (MessagingException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String loadHtmlContentFromFile(String filePath) throws IOException {
    ClassPathResource resource = new ClassPathResource(filePath);
    InputStreamReader reader = new InputStreamReader(resource.getInputStream(),
        StandardCharsets.UTF_8);
    return FileCopyUtils.copyToString(reader);
  }
}
