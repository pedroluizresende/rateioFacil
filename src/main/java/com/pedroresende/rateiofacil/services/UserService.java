package com.pedroresende.rateiofacil.services;

import com.pedroresende.rateiofacil.enums.UserStatus;
import com.pedroresende.rateiofacil.exceptions.BadRequestException;
import com.pedroresende.rateiofacil.exceptions.NotFoundUserException;
import com.pedroresende.rateiofacil.models.entities.Bill;
import com.pedroresende.rateiofacil.models.entities.User;
import com.pedroresende.rateiofacil.models.repositories.UserRepository;
import jakarta.transaction.Transactional;
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
  private final BillService billService;

  @Autowired
  public UserService(UserRepository userRepository, BillService billService) {
    this.userRepository = userRepository;
    this.billService = billService;
  }

  private void validatePassword(String password) {
    if (password == null) {
      throw new BadRequestException("Senha é obrigatória!");
    }
    if (password.length() < 6) {
      throw new BadRequestException("A senha precisa ter no mínimo 6 caracteres!");
    }
  }

  private String encodePassword(String password) {
    validatePassword(password);
    return new BCryptPasswordEncoder().encode(password);
  }

  @Override
  public User create(User user) {
    user.setPassword(encodePassword(user.getPassword()));
    user.setStatus(UserStatus.NOT_CONFIRMED);
    return userRepository.save(user);
  }

  @Override
  public User getById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(NotFoundUserException::new);
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
    userDb.setUsername(userDb.getUsername());

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

  /**
   * Método responsável por associar uma conta á um usuário.
   *
   * @param id   identificador do usuário.
   * @param bill conta a ser associada.
   * @return nova conta instancia de Bill
   */
  @Transactional
  public Bill associateBill(Long id, Bill bill) {
    User user = getById(id);
    bill.setUser(user);
    Bill billFromDb = billService.create(bill);
    user.getBills().add(billFromDb);
    userRepository.save(user);
    return billFromDb;
  }

  /**
   * Retorna todas as contas de um usuário especifico.
   *
   * @param id identificador do usuário.
   * @return lista de instancias de Bill
   */
  public List<Bill> getAllBill(Long id) {
    User user = getById(id);
    return billService.getAllByUserId(user.getId());
  }

  /**
   * Retorna uma conta de um usuário específico a partir do id da conta.
   *
   * @param id     identificador do usuário.
   * @param billId identificador da conta.
   * @return instancia de Bill
   */
  public Bill getBill(Long id, Long billId) {
    User user = getById(id);
    return billService.getById(billId);
  }

  /**
   * Método responsável por deletar uma conta.
   *
   * @param id     identificador do usuário.
   * @param billId identificador da conta.
   * @return conta deletada
   */
  @Transactional
  public Bill deleteBill(Long id, Long billId) {
    User user = getById(id);
    Bill bill = billService.delete(billId);
    user.getBills().remove(bill);
    userRepository.save(user);
    return bill;
  }

  /**
   * Método que altera o status de um usuário para confirmado.
   *
   * @param id identificador do usuário.
   * @return retorna o usuário atualizado
   */
  public User confirmUser(Long id) {
    User user = getById(id);
    user.setStatus(UserStatus.CONFIRMED);
    return userRepository.save(user);
  }
}
