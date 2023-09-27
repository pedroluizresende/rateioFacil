package com.pedroresende.rateiofacil.models.repositories;

import com.pedroresende.rateiofacil.models.entities.Bill;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositório de Bill.
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

  List<Bill> findAllByUserId(Long userId);
}
