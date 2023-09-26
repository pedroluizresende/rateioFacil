package com.pedroresende.rateiofacil.models.repositories;

import com.pedroresende.rateiofacil.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByUsername(String username);
}
