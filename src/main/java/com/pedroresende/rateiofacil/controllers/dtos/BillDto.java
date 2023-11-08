package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.enums.BillStatus;
import com.pedroresende.rateiofacil.models.entities.Bill;
import java.time.LocalDate;

/**
 * BillDto.
 */
public record BillDto(Long id, Long userId, LocalDate date, String establishment, Double total,
                      BillStatus status) {

  public Bill toEntity() {
    return new Bill(id, null, establishment, date, null, total, status);
  }
}
