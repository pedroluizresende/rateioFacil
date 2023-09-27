package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.Bill;

/**
 * BillDto.
 */
public record BillDto(Long id, Long userId, String establishment, Double total) {

  public Bill toEntity() {
    return new Bill(id, null, establishment, null, total, null);
  }
}
