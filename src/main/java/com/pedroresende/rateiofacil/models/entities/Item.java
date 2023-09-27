package com.pedroresende.rateiofacil.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "bill_id")
  private Bill bill;

  private String friend;

  private String description;

  private Double value;

  public Item() {
  }

  public Item(Long id, Bill bill, String friend, String description, Double value) {
    this.id = id;
    this.bill = bill;
    this.friend = friend;
    this.description = description;
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Bill getBill() {
    return bill;
  }

  public void setBill(Bill bill) {
    this.bill = bill;
  }

  public String getFriend() {
    return friend;
  }

  public void setFriend(String friend) {
    this.friend = friend;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }
}
