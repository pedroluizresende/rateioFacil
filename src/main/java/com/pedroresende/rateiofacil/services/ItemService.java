package com.pedroresende.rateiofacil.services;

import com.pedroresende.rateiofacil.exceptions.NotFoundItemException;
import com.pedroresende.rateiofacil.models.entities.Item;
import com.pedroresende.rateiofacil.models.repositories.ItemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço da entidade Item.
 */
@Service
public class ItemService implements BasicService<Item> {

  private final ItemRepository intemRepository;

  @Autowired
  public ItemService(ItemRepository orderRepository) {
    this.intemRepository = orderRepository;
  }


  @Override
  public Item create(Item item) {
    return intemRepository.save(item);
  }

  @Override
  public Item getById(Long id) {
    Optional<Item> optionalItem = intemRepository.findById(id);

    if (optionalItem.isEmpty()) {
      throw new NotFoundItemException();
    }

    return optionalItem.get();
  }

  @Override
  public List<Item> getAll() {
    return null;
  }

  @Override
  public Item update(Long id, Item entity) {
    return null;
  }

  @Override
  public Item delete(Long id) {
    Item item = getById(id);
    intemRepository.deleteById(item.getId());
    return item;
  }

  public List<Item> getAllByBillId(Long id) {
    return intemRepository.findAllByBillId(id);
  }

  public List<Item> getByFriend(String friend) {
    return intemRepository.findAllByFriend(friend);
  }
}
