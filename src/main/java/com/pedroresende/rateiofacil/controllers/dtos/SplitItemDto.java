package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.Item;
import java.util.List;

/**
 * Classe SplitItemDto.
 *
 * @param friends     array de amigos que dividiram um pedido.
 * @param description descrição do pedido.
 * @param value       valor do pedido.
 */
public record SplitItemDto(List<String> friends, String description, Double value) {

  /**
   * Método responsável por dividir o valor do pedido para todos os amigos presentes no array.
   *
   * @return retornar uma lista de instancias de Item
   */
  public List<Item> toEntityList() {
    Double splitValue = value / (friends.size());
    List<Item> itemList = friends.stream().map(
        friend -> new Item(null, null, friend, description, splitValue)
    ).toList();
    return itemList;
  }
}
