package com.pedroresende.rateiofacil.models.entities;

import com.pedroresende.rateiofacil.enums.BillStatus;
import com.pedroresende.rateiofacil.listeners.BillListener;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidade Bill.
 */
@Entity
@Table(name = "bills")
@EntityListeners({AuditingEntityListener.class, BillListener.class})
public class Bill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String establishment;
  @CreatedDate
  private LocalDate date;

  @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Item> items;

  private Double total;

  private BillStatus status;

  private String imgUrl;


  public Bill() {
  }

  /**
   * Método construtor com parametros.
   */
  public Bill(Long id, User user, String establishment, LocalDate date, List<Item> items,
      Double total, BillStatus status, String imgUrl) {
    this.id = id;
    this.user = user;
    this.establishment = establishment;
    this.date = date;
    this.total = total;
    this.status = status;
    this.items = items;
    this.imgUrl = imgUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getEstablishment() {
    return establishment;
  }

  public void setEstablishment(String establishment) {
    this.establishment = establishment;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public BillStatus getStatus() {
    return status;
  }

  public void setStatus(BillStatus status) {
    this.status = status;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
