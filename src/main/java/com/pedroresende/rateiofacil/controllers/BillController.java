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

  @PostMapping()
  ResponseEntity<ResponseDto<BillDto>> create(@RequestBody BillDto billDto) {
    Bill bill = billService.create(billDto.toEntity());
    BillDto billDtoFromDb = toBillDto(bill);

    ResponseDto<BillDto> responseDto = new ResponseDto<>(
        "Usuário criado com sucesso", billDtoFromDb
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  private BillDto toBillDto(Bill bill) {

    if (bill.getUser() == null) {
      return new BillDto(bill.getId(), null, bill.getEstablishment());
    }

    return new BillDto(bill.getId(), bill.getUser().getId(), bill.getEstablishment());
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
