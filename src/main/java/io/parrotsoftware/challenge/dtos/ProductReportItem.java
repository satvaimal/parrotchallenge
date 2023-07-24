package io.parrotsoftware.challenge.dtos;

import java.math.BigDecimal;

public class ProductReportItem {

  private String name;

  private Long quantity;

  private BigDecimal total;

  public ProductReportItem(String name, Long quantity, BigDecimal total) {
    this.name = name;
    this.quantity = quantity;
    this.total = total;
  }

  public String getName() {
    return this.name;
  }

  public Long getQuantity() {
    return this.quantity;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

}
