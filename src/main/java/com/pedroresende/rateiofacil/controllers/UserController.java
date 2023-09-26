package com.pedroresende.rateiofacil.controllers;

import com.pedroresende.rateiofacil.controllers.dtos.CreationUserDto;
import com.pedroresende.rateiofacil.controllers.dtos.ResponseDto;
import com.pedroresende.rateiofacil.controllers.dtos.UserDto;
import com.pedroresende.rateiofacil.models.entities.User;
import com.pedroresende.rateiofacil.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
  public ResponseEntity<ResponseDto<UserDto>> create(
      @RequestBody CreationUserDto creationUserDto) {
    User user = userService.create(creationUserDto.toEntity());

    UserDto userDto = toUserDto(user);

    ResponseDto<UserDto> responseDto = new ResponseDto<>(
        "Usuário criado com sucesso!", userDto
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  /**
   * Método auxiliar para converter User em UserDto.
   *
   * @param user user a ser convertido.
   * @return userDto a ser retornado.
   */
  private UserDto toUserDto(User user) {
    return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getUsername());
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
   * Mapeamento da rota GET users/{id}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getById(@PathVariable Long id) {
    User user = userService.getById(id);

    return ResponseEntity.ok(toUserDto(user));
  }

  /**
   * Mapeamento da rota PUT users/{id}.
   */
  @PutMapping("/{id}")
  public ResponseEntity<ResponseDto<UserDto>> update(@PathVariable Long id,
      @RequestBody CreationUserDto creationUserDto) {
    User user = userService.update(id, creationUserDto.toEntity());
    ResponseDto<UserDto> responseDto = new ResponseDto<>(
        "Usuário atualizado com sucesso", toUserDto(user)
    );

    return ResponseEntity.ok(responseDto);
  }

  /**
   * Mapeamento da rota DELETE users/{id}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto<UserDto>> delete(@PathVariable Long id) {
    User user = userService.delete(id);
    UserDto userDto = toUserDto(user);
    ResponseDto<UserDto> responseDto = new ResponseDto<>("Usuário deletado!", userDto);

    return ResponseEntity.ok(responseDto);
  }
}
