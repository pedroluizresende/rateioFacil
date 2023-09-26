package com.pedroresende.rateiofacil.services;

import com.pedroresende.rateiofacil.exceptions.NotFoundUserException;
import com.pedroresende.rateiofacil.models.entities.User;
import com.pedroresende.rateiofacil.models.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço da rota users.
 */
@Service
public class UserService implements BasicService<User>, UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User create(User user) {
    String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
    user.setPassword(hashedPassword);
    return userRepository.save(user);
  }

  @Override
  public User getById(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);

    if (optionalUser.isEmpty()) {
      throw new NotFoundUserException();
    }

    return optionalUser.get();
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User update(Long id, User user) {
    User userDb = getById(id);
    userDb.setName(user.getName());
    userDb.setEmail(user.getEmail());
    userDb.setUsername(user.getUsername());
    userDb.setPassword(user.getPassword());

    return userRepository.save(userDb);
  }

  @Override
  public User delete(Long id) {
    User user = getById(id);
    userRepository.deleteById(id);
    return user;
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findUserByUsername(username);
  }
}
