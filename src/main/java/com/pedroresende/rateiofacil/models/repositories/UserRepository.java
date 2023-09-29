package com.pedroresende.rateiofacil.models.repositories;

import com.pedroresende.rateiofacil.models.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByUsername(String username);
}
