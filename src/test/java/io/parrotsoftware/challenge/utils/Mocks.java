package io.parrotsoftware.challenge.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.parrotsoftware.challenge.dtos.OrderDto;
import io.parrotsoftware.challenge.dtos.ProductDto;
import io.parrotsoftware.challenge.dtos.ProductReportItem;
import io.parrotsoftware.challenge.entities.User;

public class Mocks {

  public static User mockUser() {
    return new User(1L, "john@parrotsoftware.io", "John Doe");
  }

  public static OrderDto mockOrderDto() {

    OrderDto orderDto = new OrderDto();
    orderDto.setUserId(1L);
    orderDto.setClientName("John Doe");
    List<ProductDto> products = new ArrayList<>();
    products.add(mockProductDto());
    orderDto.setProducts(products);
    return orderDto;

  }

  public static ProductDto mockProductDto() {
    return new ProductDto("Cookies", new BigDecimal(19.99), 5);
  }

  public static List<ProductReportItem> mockProductReportItems() {

    List<ProductReportItem> items = new ArrayList<>();
    items.add(new ProductReportItem("Cookies", 5L, new BigDecimal(10)));
    return items;

  }

}

