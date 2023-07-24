package io.parrotsoftware.challenge.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class OrderDto {

  @NotNull(message = "User ID is not present")
  private Long userId;

  @NotNull(message = "Client name is not present")
  @Size(min = 1, max = 50, message = "Client name must be between 1 and 50 characters")
  private String clientName;

  @Valid
  @NotEmpty(message = "Product list is empty")
  private List<ProductDto> products;

  @Null(message = "Total is not allowed")
  private BigDecimal total;

  private Date creationDate;

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getClientName() {
    return this.clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setProducts(List<ProductDto> products) {
    this.products = products;
  }

  public List<ProductDto> getProducts() {
    return this.products;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

}
