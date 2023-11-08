package com.pedroresende.rateiofacil.listeners;

import com.pedroresende.rateiofacil.exceptions.BadRequestException;
import com.pedroresende.rateiofacil.models.entities.Bill;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

@Component
public class BillListener {

  @PrePersist
  public void validateNewBill(Bill bill) {
    if (bill.getEstablishment() == null) {
      throw new BadRequestException("Nome do estabelecimento é obrigatório!");
    }

    if (bill.getEstablishment().length() < 2) {
      throw new BadRequestException("Nome do estabelecimento deve ter no mínimo 2 caracteres!");
    }
  }
}
