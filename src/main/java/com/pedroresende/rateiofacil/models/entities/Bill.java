package com.pedroresende.rateiofacil.models.entities;

import com.pedroresende.rateiofacil.enums.BillStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.springframework.data.annotation.CreatedDate;

/**
 * Entidade Bill.
 */
@Entity
@Table(name = "bills")
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

  private Double total;

  private BillStatus status;


  public Bill() {
  }

  /**
   * Método construtor com parametros.
   */
  public Bill(Long id, User user, String establishment, LocalDate date, Double total,
      BillStatus status) {
    this.id = id;
    this.user = user;
    this.establishment = establishment;
    this.date = date;
    this.total = total;
    this.status = status;
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
}
