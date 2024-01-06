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

  private final ItemRepository itemRepository;

  @Autowired
  public ItemService(ItemRepository orderRepository) {
    this.itemRepository = orderRepository;
  }


  @Override
  public Item create(Item item) {
    return itemRepository.save(item);
  }

  @Override
  public Item getById(Long id) {
    return itemRepository.findById(id).orElseThrow(NotFoundItemException::new);
  }

  @Override
  public List<Item> getAll() {
    return null;
  }

  @Override
  public Item update(Long id, Item entity) {
    Item item = getById(id);
    item.setDescription(entity.getDescription());
    item.setValue(entity.getValue());
    item.setFriend(entity.getFriend());
    return itemRepository.save(item);
  }

  @Override
  public Item delete(Long id) {
    Item item = getById(id);
    itemRepository.deleteById(item.getId());
    return item;
  }

  public List<Item> getAllByBillId(Long id) {
    return itemRepository.findAllByBillId(id);
  }

  public List<Item> getByFriendAndBillId(String friend, Long billId) {
    return itemRepository.findAllByFriendAndBillId(friend, billId);
  }
}
