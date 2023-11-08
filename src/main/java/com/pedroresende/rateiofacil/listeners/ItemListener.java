package com.pedroresende.rateiofacil.listeners;

import com.pedroresende.rateiofacil.exceptions.BadRequestException;
import com.pedroresende.rateiofacil.models.entities.Item;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

@Component
public class ItemListener {

  private void validateFriend(String friend) {
    if (friend == null) {
      throw new BadRequestException("O nome do amigo é obrigatório!");
    }

    if (friend.length() < 2) {
      throw new BadRequestException("O nome do amigo deve ter no mínimo 2 caracteres");
    }
  }

  private void validateDescription(String description) {
    if (description == null) {
      throw new BadRequestException("Descrição é obrigatória!");
    }

    if (description.length() < 3) {
      throw new BadRequestException("Descrição deve ter no mínimo 3 caracteres!");
    }
  }

  private void validateValue(Double value) {
    if (value == null) {
      throw new BadRequestException("Valor é obrigatório!");
    }

    if (value <= 0) {
      throw new BadRequestException("Valor deve ser um número maior que zero!");
    }
  }

  @PrePersist
  void validateNewItem(Item item) {
    validateFriend(item.getFriend());
    validateDescription(item.getDescription());
    validateValue(item.getValue());
  }
}
