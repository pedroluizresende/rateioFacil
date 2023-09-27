package com.pedroresende.rateiofacil.services;

import com.pedroresende.rateiofacil.enums.BillStatus;
import com.pedroresende.rateiofacil.exceptions.NotFoundBillException;
import com.pedroresende.rateiofacil.models.entities.Bill;
import com.pedroresende.rateiofacil.models.repositories.BillRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço da rota /bill.
 */
@Service
public class BillService implements BasicService<Bill> {

  private final BillRepository billRepository;

  @Autowired
  public BillService(BillRepository billRepository) {
    this.billRepository = billRepository;
  }

  @Override
  public Bill create(Bill bill) {
    bill.setStatus(BillStatus.OPEN);
    bill.setTotal(0.0);
    return billRepository.save(bill);
  }

  @Override
  public Bill getById(Long id) {
    Optional<Bill> optionalBill = billRepository.findById(id);
    if (optionalBill.isEmpty()) {
      throw new NotFoundBillException();
    }
    return optionalBill.get();
  }

  @Override
  public List<Bill> getAll() {
    return billRepository.findAll();
  }

  @Override
  public Bill update(Long id, Bill bill) {
    Bill billFromDb = getById(id);
    billFromDb.setEstablishment(bill.getEstablishment());

    return billRepository.save(billFromDb);
  }

  @Override
  public Bill delete(Long id) {
    Bill bill = getById(id);

    billRepository.deleteById(id);
    return bill;
  }

  public List<Bill> getAllByUserId(Long userId) {
    return billRepository.findAllByUserId(userId);
  }
}
