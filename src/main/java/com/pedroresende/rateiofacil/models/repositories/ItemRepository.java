package com.pedroresende.rateiofacil.models.repositories;

import com.pedroresende.rateiofacil.models.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
