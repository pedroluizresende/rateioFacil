package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.Item;
import java.time.LocalDate;
import java.util.List;

/**
 * Classee BillDtoWithItems.
 */
public record BillDtoWithitems(Long id, Long userId, LocalDate date, List<Item> items,
                               String establishment, Double total) {

}
