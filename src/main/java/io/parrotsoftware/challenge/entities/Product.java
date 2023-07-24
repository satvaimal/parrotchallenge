package io.parrotsoftware.challenge.entities;

import io.parrotsoftware.challenge.dtos.ProductReportItem;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@NamedNativeQuery(
    name = "Product.getProductsReport", 
    query = "select p.name as name"+
    ", sum(oi.quantity) as quantity" +
    ", sum(oi.quantity) * p.price as total" +
    " from order_items as oi" +
    " inner join orders as o on oi.order_id = o.id" +
    " inner join products as p on oi.product_id = p.id" +
    " where o.creation_date between :from and :to" +
    " group by p.name, p.price" +
    " order by total desc",
    resultSetMapping = "mapping.ProductReportItem")
@SqlResultSetMapping(
  name = "mapping.ProductReportItem",
  classes = @ConstructorResult(targetClass = ProductReportItem.class,
    columns = {
      @ColumnResult(name = "name"),
      @ColumnResult(name = "quantity"),
      @ColumnResult(name = "total")
    }
  )
)
@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull(message = "User is not present")
  private User user;

  @Column(nullable = false)
  @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
  private String name;

  @Column(nullable = false)
  @NotNull(message = "Price is not present")
  @Positive(message = "Price must be positive")
  private BigDecimal price;

  public Product() {}

  public Product(User user, String name, BigDecimal price) {
    this.user = user;
    this.name = name;
    this.price = price;
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

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getPrice() {
    return this.price;
  }

}
