package io.parrotsoftware.challenge.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDto {

  @NotNull(message = "Name is not present")
  @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
  private String name;

  @NotNull(message = "Price is not present")
  @Positive(message = "Price must be positive")
  private BigDecimal price;

  @NotNull(message = "Quantity is not present")
  @Positive(message = "Quantity must be positive")
  private Integer quantity;

  public ProductDto(String name, BigDecimal price, Integer quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
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

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getQuantity() {
    return this.quantity;
  }

}

