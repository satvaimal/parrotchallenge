package io.parrotsoftware.challenge.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  @NotNull(message = "Order is not present")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  @NotNull(message = "Product is not present")
  private Product product;

  @Column(nullable = false)
  @NotNull(message = "Quantity is not present")
  @Positive(message = "Quantity must be positive")
  private Integer quantity;

  public OrderItem() {}

  public OrderItem(Order order, Product product, Integer quantity) {
    this.order = order;
    this.product = product;
    this.quantity = quantity;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Order getOrder() {
    return this.order;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Product getProduct() {
    return this.product;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getQuantity() {
    return this.quantity;
  }

}

