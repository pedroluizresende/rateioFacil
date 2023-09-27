package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.Bill;

public record BillDto(Long id,Long userId, String establishment) {

  public Bill toEntity() {
    return new Bill(id, null, establishment, null, null, null);
  }
}
