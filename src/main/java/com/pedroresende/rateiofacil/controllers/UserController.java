package com.pedroresende.rateiofacil.controllers;

import com.pedroresende.rateiofacil.controllers.dtos.BillDto;
import com.pedroresende.rateiofacil.controllers.dtos.BillDtoWithitems;
import com.pedroresende.rateiofacil.controllers.dtos.CreationUserDto;
import com.pedroresende.rateiofacil.controllers.dtos.ResponseDto;
import com.pedroresende.rateiofacil.controllers.dtos.UpdateUserDto;
import com.pedroresende.rateiofacil.controllers.dtos.UserDto;
import com.pedroresende.rateiofacil.exceptions.NotAuthorizeUserException;
import com.pedroresende.rateiofacil.models.entities.Bill;
import com.pedroresende.rateiofacil.models.entities.User;
import com.pedroresende.rateiofacil.services.UserService;
import com.pedroresende.rateiofacil.utils.Calculator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Camada de controller da rota /users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Mapemaneto da rota POST /users.
   */
  @PostMapping()
  public ResponseEntity<ResponseDto<UserDto>> create(@RequestBody CreationUserDto creationUserDto) {
    User user = userService.create(creationUserDto.toEntity());

    UserDto userDto = toUserDto(user);

    ResponseDto<UserDto> responseDto = new ResponseDto<>("Usuário criado com sucesso!", userDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  /**
   * Método auxiliar para converter User em UserDto.
   *
   * @param user user a ser convertido.
   * @return userDto a ser retornado.
   */
  private UserDto toUserDto(User user) {
    return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getUsername(),
        user.getStatus());
  }

  /**
   * Mapeamento da rota GET /users.
   */
  @GetMapping
  @Secured("admin")
  public ResponseEntity<List<UserDto>> getAll() {
    List<User> users = userService.getAll();

    List<UserDto> userDtos = users.stream().map(user -> toUserDto(user)).toList();

    return ResponseEntity.ok(userDtos);
  }

  /**
   * Mapeamento da rota /users/confirmation, responsável por confirmar cadastro de um usuário
   * alterando seu status para confirmed.
   *
   * @param user usuário a ser confirmado.
   * @return status 200 e mensagem de sucesso junto com informações do usuário
   */
  @PutMapping("/confirmation")
  public ResponseEntity<ResponseDto<UserDto>> confirmUser(
      @AuthenticationPrincipal User user
  ) {
    UserDto userDto = toUserDto(userService.confirmUser(user.getId()));

    ResponseDto<UserDto> responseDto = new ResponseDto<>("Email confirmado com sucesso!", userDto);

    return ResponseEntity.ok(responseDto);
  }

  /**
   * Mapeamento da rota GET users/{id}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getById(@PathVariable Long id,
      @AuthenticationPrincipal User authUser) {
    validateUserPermission(id, authUser);
    User user = userService.getById(id);
    UserDto userDto = toUserDto(user);
    return ResponseEntity.ok(toUserDto(user));
  }

  /**
   * Mapeamento da rota PUT users/{id}.
   */
  @PutMapping("/{id}")
  public ResponseEntity<ResponseDto<UserDto>> update(@PathVariable Long id,
      @RequestBody UpdateUserDto updateUserDto,
      @AuthenticationPrincipal User authUser) {
    validateUserPermission(id, authUser);
    User user = userService.update(id, updateUserDto.toEntity());
    ResponseDto<UserDto> responseDto = new ResponseDto<>("Usuário atualizado com sucesso",
        toUserDto(user));

    return ResponseEntity.ok(responseDto);
  }

  /**
   * Mapeamento da rota DELETE users/{id}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<UserDto>> delete(@PathVariable Long id,
      @AuthenticationPrincipal User authUser) {
    validateUserPermission(id, authUser);
    User user = userService.delete(id);
    UserDto userDto = toUserDto(user);
    ResponseDto<UserDto> responseDto = new ResponseDto<>("Usuário deletado!", userDto);

    return ResponseEntity.ok(responseDto);
  }

  /**
   * Mapeamento da rota POST /users/{id}/bills.
   *
   * @param id      identificador do usuário.
   * @param billDto informações da conta.
   * @return mensagem de sucesso e uma instancia de BillDto
   */
  @PostMapping("/{id}/bills")
  @CrossOrigin(value = "$frontend.url")
  public ResponseEntity<ResponseDto<BillDto>> createBill(@PathVariable Long id,
      @RequestBody BillDto billDto,
      @AuthenticationPrincipal User authUser) {
    validateUserPermission(id, authUser);
    Bill bill = userService.associateBill(id, billDto.toEntity());

    BillDto billDtoFromDb = toBillDto(bill);

    ResponseDto<BillDto> responseDto = new ResponseDto<>("Conta associada com sucesso",
        billDtoFromDb);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }


  private BillDto toBillDto(Bill bill) {

    if (bill.getUser() == null) {
      return new BillDto(bill.getId(), null, bill.getDate(), bill.getEstablishment(),
          Calculator.sumValues(bill.getTotal(), 0.0), bill.getStatus(), bill.getImgUrl());
    }

    return new BillDto(bill.getId(), bill.getUser().getId(), bill.getDate(),
        bill.getEstablishment(), bill.getTotal(), bill.getStatus(), bill.getImgUrl());
  }

  private BillDtoWithitems toBillDtoWithitems(Bill bill) {
    if (bill.getItems() == null) {
      return new BillDtoWithitems(bill.getId(), bill.getUser().getId(), bill.getDate(),
          null, bill.getEstablishment(), bill.getTotal());
    }
    return new BillDtoWithitems(bill.getId(), bill.getUser().getId(), bill.getDate(),
        bill.getItems(), bill.getEstablishment(), bill.getTotal());
  }

  @GetMapping("/{id}/bills")
  ResponseEntity<List<BillDto>> getAllBills(@PathVariable Long id,
      @AuthenticationPrincipal User authUser) {
    validateUserPermission(id, authUser);
    List<Bill> bills = userService.getAllBill(id);
    List<BillDto> billDtos = bills.stream().map(bill -> toBillDto(bill)).toList();

    return ResponseEntity.ok(billDtos);
  }

  @GetMapping("/{id}/bills/{billId}")
  ResponseEntity<BillDto> getBill(@PathVariable Long id, @PathVariable Long billId,
      @AuthenticationPrincipal User authUser) {
    validateUserPermission(id, authUser);
    Bill bill = userService.getBill(id, billId);

    return ResponseEntity.ok(toBillDto(bill));
  }

  @DeleteMapping("/{id}/bills/{billId}")
  ResponseEntity<ResponseDto<BillDto>> deleteBill(@PathVariable Long id, @PathVariable Long billId,
      @AuthenticationPrincipal User authUser) {
    validateUserPermission(id, authUser);
    Bill bill = userService.deleteBill(id, billId);
    BillDto billDto = toBillDto(bill);
    ResponseDto<BillDto> responseDto = new ResponseDto<>("Conta deletada com sucesso", billDto);
    return ResponseEntity.ok(responseDto);
  }

  private void validateUserPermission(Long pathId, User authUser) {
    if (!authUser.getRole().equals("admin")) {
      if (!authUser.getId().equals(pathId)) {
        throw new NotAuthorizeUserException();
      }
    }
  }
}
