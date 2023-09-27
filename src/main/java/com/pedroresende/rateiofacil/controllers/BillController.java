package com.pedroresende.rateiofacil.controllers;

import com.pedroresende.rateiofacil.controllers.dtos.BillDto;
import com.pedroresende.rateiofacil.controllers.dtos.ResponseDto;
import com.pedroresende.rateiofacil.models.entities.Bill;
import com.pedroresende.rateiofacil.services.BillService;
import java.util.List;
import org.hibernate.boot.model.internal.ListBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bills")
public class BillController {

  private final BillService billService;

  @Autowired
  public BillController(BillService billService) {
    this.billService = billService;
  }

  private BillDto toBillDto(Bill bill) {

    if (bill.getUser() == null) {
      return new BillDto(bill.getId(), null, bill.getEstablishment(), bill.getTotal());
    }

    return new BillDto(bill.getId(), bill.getUser().getId(), bill.getEstablishment(),
        bill.getTotal());
  }

  @GetMapping
  @Secured("admin")
  ResponseEntity<List<BillDto>> getAll() {
    List<Bill> bills = billService.getAll();

    List<BillDto> billDtos = bills.stream().map(
        bill -> toBillDto(bill)
    ).toList();
    return ResponseEntity.ok(billDtos);
  }
}
