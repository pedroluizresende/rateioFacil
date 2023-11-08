package com.pedroresende.rateiofacil.models.repositories;

import com.pedroresende.rateiofacil.models.entities.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface ItemRepository.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

  List<Item> findAllByBillId(Long billId);

  List<Item> findAllByFriendAndBillId(String friend, Long billId);
}
