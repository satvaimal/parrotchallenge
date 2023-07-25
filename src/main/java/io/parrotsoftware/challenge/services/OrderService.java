package io.parrotsoftware.challenge.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.parrotsoftware.challenge.dtos.OrderDto;
import io.parrotsoftware.challenge.dtos.ProductDto;
import io.parrotsoftware.challenge.entities.Order;
import io.parrotsoftware.challenge.entities.OrderItem;
import io.parrotsoftware.challenge.entities.OrderItemRepository;
import io.parrotsoftware.challenge.entities.OrderRepository;
import io.parrotsoftware.challenge.entities.Product;
import io.parrotsoftware.challenge.entities.ProductRepository;
import io.parrotsoftware.challenge.entities.User;
import io.parrotsoftware.challenge.entities.UserRepository;
import io.parrotsoftware.challenge.errors.MyError;
import io.parrotsoftware.challenge.errors.MyValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public OrderDto processOrder(OrderDto order) {

    validateUserId(order.getUserId());
    Map<String, ProductDto> uniqueProducts = new HashMap<String, ProductDto>();
    order.setTotal(new BigDecimal(0));
    for (ProductDto product : order.getProducts()) {
      String name = product.getName();
      if (uniqueProducts.get(name) == null) {
        uniqueProducts.put(name, new ProductDto(
            product.getName(), product.getPrice(), 0));
      }
      ProductDto productDto = uniqueProducts.get(name);
      productDto.setQuantity(productDto.getQuantity() + product.getQuantity());
      order.setTotal(order.getTotal().add(productDto.getPrice().multiply(
          new BigDecimal(product.getQuantity()))));
    }
    order.setProducts(new ArrayList<ProductDto>(uniqueProducts.values()));
    saveOrder(order);
    return order;

  }

  private void validateUserId(Long userId) {

    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new MyValidationException(
          new MyError("userId", "User Id does not exist"));
    }

  }

  private void saveOrder(OrderDto orderDto) {

    User user = userRepository.findById(orderDto.getUserId()).get();
    Date creationDate = orderDto.getCreationDate() != null ?
        orderDto.getCreationDate() : new Date();
    orderDto.setCreationDate(creationDate);
    Order order = orderRepository.save(new Order(
        user, orderDto.getClientName(), creationDate, orderDto.getTotal()));
    for (ProductDto productDto : orderDto.getProducts()) {
      Product product = productRepository.findFirstByName(productDto.getName());
      if (product == null) {
        product = productRepository.save(new Product(
            user, productDto.getName(), productDto.getPrice()));
      }
      orderItemRepository.save(new OrderItem(
          order, product, productDto.getQuantity()));
    }

  }

}

