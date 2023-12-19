package com.pedroresende.rateiofacil.services;

import com.pedroresende.rateiofacil.controllers.dtos.FriendConsumptionDto;
import com.pedroresende.rateiofacil.enums.BillStatus;
import com.pedroresende.rateiofacil.exceptions.NotFoundBillException;
import com.pedroresende.rateiofacil.models.entities.Bill;
import com.pedroresende.rateiofacil.models.entities.Item;
import com.pedroresende.rateiofacil.models.repositories.BillRepository;
import com.pedroresende.rateiofacil.utils.Calculator;
import com.pedroresende.rateiofacil.utils.Result;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
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
  private final ItemService itemService;

  @Autowired
  public BillService(BillRepository billRepository, ItemService itemService) {
    this.billRepository = billRepository;
    this.itemService = itemService;
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

  /**
   * Método responsável por finalizar uma conta.
   *
   * @param id identificador da conta.
   * @return instancia de Bill
   */
  public Bill finish(Long id) {
    Bill bill = getById(id);

    bill.setStatus(BillStatus.CLOSED);

    return billRepository.save(bill);
  }

  public List<Bill> getAllByUserId(Long userId) {
    return billRepository.findAllByUserId(userId);
  }

  /**
   * Método responsável por adicionar um item a uma conta.
   */

  @Transactional
  public Item addItem(Long id, Item item) {
    Bill bill = getById(id);
    item.setBill(bill);
    final Item itemFromDb = itemService.create(item);
    bill.getItems().add(item);
    bill.setTotal(itemFromDb.getValue() + bill.getTotal());
    billRepository.save(bill);
    itemFromDb.getValue();
    return itemFromDb;
  }

  /**
   * Método responsável por recuperar items de uma conta.
   */
  public List<Item> getitems(Long id) {
    Bill bill = getById(id);

    return itemService.getAllByBillId(bill.getId());
  }

  /**
   * Método responsável por remover um item de uma conta.
   */
  @Transactional
  public Item removeItem(Long id, Long itemId) {
    Bill bill = getById(id);
    Item item = itemService.delete(id);
    bill.getItems().remove(item);
    billRepository.save(bill);

    return item;
  }

  /**
   * Método responsável por retornar um item específico pelo seu ID.
   */
  public Item getItemById(Long id, Long itemId) {
    getById(id);

    return itemService.getById(itemId);
  }

  /**
   * Método responsável por retornar o valor calculado da conta.
   */
  public Result calculate(Long id) {
    Bill bill = getById(id);
    Result result = new Result(bill.getUser().getId(), bill.getId(), bill.getEstablishment(),
        bill.getDate(), bill.getTotal(), bill.getItems());

    return result;
  }

  /**
   * Método responsável por retornar uma lista com toda a consumação de um amigo.
   */
  public List<Item> getFriendConsumption(Long id, String friend) {
    getById(id);

    List<Item> items = itemService.getByFriendAndBillId(friend, id);

    return items;
  }

  /**
   * Método responsável por adicionar pedidos que foram dividios entre amigos.
   *
   * @param id       identificador da conta.
   * @param itemList lista de instancias de Item.
   * @return lista de instancias de item
   */
  public List<Item> addSplitItem(Long id, List<Item> itemList) {
    List<Item> itemListFromDb = new ArrayList<>();
    for (Item item : itemList) {
      Item itemFromDb = addItem(id, item);
      itemListFromDb.add(itemFromDb);
    }
    return itemListFromDb;
  }

  /**
   * Método responsável por adicionar uma url de imagem à uma conta.
   *
   * @param id     identificador da conta no banco.
   * @param imgUrl string que representa a url da imagem.
   * @return instancia objeto da classe Bill atualizado
   */
  public Bill addImgUrl(Long id, String imgUrl) {
    Bill bill = getById(id);

    bill.setImgUrl(imgUrl);

    return billRepository.save(bill);
  }
}
