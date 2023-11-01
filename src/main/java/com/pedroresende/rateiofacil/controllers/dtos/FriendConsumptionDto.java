package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.Item;
import java.util.List;

/**
 * Classe FriendConsumptionDto.
 */
public record FriendConsumptionDto(String name, List<ItemDto> items, Double value,
                                   Double taxService,
                                   Double total) {

}
