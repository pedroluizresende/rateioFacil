package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.Item;

/**
 * Classe ItemDto.
 */
public record ItemDto(Long id, Long billId, String friend, String description, Double value) {

  public Item toEntity() {
    return new Item(id, null, friend, description, value);
  }
}
