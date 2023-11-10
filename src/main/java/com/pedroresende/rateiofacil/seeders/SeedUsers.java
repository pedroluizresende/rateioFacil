package com.pedroresende.rateiofacil.seeders;

import com.pedroresende.rateiofacil.enums.UserStatus;
import com.pedroresende.rateiofacil.models.entities.User;
import com.pedroresende.rateiofacil.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeder para criação de usuário ADMIN.
 */
@Component
public class SeedUsers implements CommandLineRunner {

  private UserRepository userRepository;

  @Autowired
  public SeedUsers(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    User user = new User(null, "Admin", "admin@admin.com", "admin", "admin123", "admin", null,
        UserStatus.CONFIRMED);
    String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
    user.setPassword(hashedPassword);
    userRepository.save(user);
  }
}
