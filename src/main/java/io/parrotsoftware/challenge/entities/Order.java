package io.parrotsoftware.challenge.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull(message = "User is not present")
  private User user;

  @Column(nullable = false)
  @NotNull(message = "Client name is not present")
  @Size(min = 1, max = 50, message = "Client name must be between 1 and 50 characters")
  private String clientName;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  @NotNull(message = "Creation date is not present")
  private Date creationDate;

  @Column(nullable = false)
  @NotNull(message = "Total is not present")
  private BigDecimal total;

  public Order() {}

  public Order(User user, String clientName, Date creationDate, BigDecimal total) {
    this.user = user;
    this.clientName = clientName;
    this.creationDate = creationDate;
    this.total = total;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getClientName() {
    return this.clientName;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

}
