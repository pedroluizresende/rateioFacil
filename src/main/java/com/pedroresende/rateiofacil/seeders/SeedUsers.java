package com.pedroresende.rateiofacil.seeders;

import com.pedroresende.rateiofacil.enums.UserStatus;
import com.pedroresende.rateiofacil.models.entities.User;
import com.pedroresende.rateiofacil.models.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeder para criação de usuário ADMIN.
 */
@Component
public class SeedUsers implements CommandLineRunner {

  private final UserRepository userRepository;
  private final Environment env;

  @Autowired
  public SeedUsers(UserRepository userRepository, Environment env) {
    this.userRepository = userRepository;
    this.env = env;
  }

  @Override
  public void run(String... args) {
    System.out.println("-----------------------Seeders---------------------");

    if (userRepository.count() == 0) {
      System.out.println("Criando admin!");
      String adminName = env.getProperty("admin.name", "Admin");
      String adminEmail = env.getProperty("admin.email", "admin@admin.com");
      String adminUsername = env.getProperty("admin.username", "admin");
      String adminPassword = env.getProperty("admin.password", "admin123");

      User user = new User(null, adminName, adminEmail, adminUsername, adminPassword, "admin", null,
          UserStatus.CONFIRMED);
      String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
      user.setPassword(hashedPassword);
      userRepository.save(user);
    } else {
      System.out.println("Admin já existente!");
    }
  }
}
