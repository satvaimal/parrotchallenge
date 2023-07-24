package io.parrotsoftware.challenge.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import io.parrotsoftware.challenge.utils.Mocks;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceProcessOrderTest {

  @InjectMocks
  private OrderService orderService;

  @Mock
  private OrderItemRepository orderItemRepository;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private UserRepository userRepository;

  @Test
  public void orderSuccessfullyProcessed() throws Exception {

    when(userRepository.findById(anyLong()))
        .thenReturn(Optional.of(new User()));
    when(orderRepository.save(any(Order.class)))
        .thenReturn(new Order());
    when(productRepository.save(any(Product.class)))
        .thenReturn(new Product());
    when(orderItemRepository.save(any(OrderItem.class)))
        .thenReturn(new OrderItem());
    OrderDto orderDto = Mocks.mockOrderDto();
    assertThat(orderDto.getCreationDate()).isNull();
    OrderDto processedOrderDto = orderService.processOrder(orderDto);
    assertThat(processedOrderDto).isNotNull();
    assertThat(processedOrderDto).isEqualTo(orderDto);
    assertThat(processedOrderDto.getCreationDate()).isNotNull();
    assertThat(processedOrderDto.getUserId())
        .isEqualTo(orderDto.getUserId());
    assertThat(processedOrderDto.getClientName())
        .isEqualTo(orderDto.getClientName());
    assertThat(processedOrderDto.getProducts())
        .isEqualTo(orderDto.getProducts());

  }

  @Test
  public void orderWithDuplicatedProducts() throws Exception {

    when(userRepository.findById(anyLong()))
        .thenReturn(Optional.of(new User()));
    when(orderRepository.save(any(Order.class)))
        .thenReturn(new Order());
    when(productRepository.save(any(Product.class)))
        .thenReturn(new Product());
    when(orderItemRepository.save(any(OrderItem.class)))
        .thenReturn(new OrderItem());
    OrderDto orderDto = Mocks.mockOrderDto();
    ProductDto productDto = orderDto.getProducts().get(0);
    orderDto.getProducts().add(Mocks.mockProductDto());
    orderDto.getProducts().add(Mocks.mockProductDto());
    assertThat(orderDto.getProducts().size()).isEqualTo(3);
    OrderDto processedOrderDto = orderService.processOrder(orderDto);
    assertThat(processedOrderDto).isNotNull();
    assertThat(processedOrderDto).isEqualTo(orderDto);
    assertThat(processedOrderDto.getUserId())
        .isEqualTo(orderDto.getUserId());
    assertThat(processedOrderDto.getClientName())
        .isEqualTo(orderDto.getClientName());
    assertThat(processedOrderDto.getCreationDate()).isNotNull();
    assertThat(orderDto.getTotal()).isEqualTo(productDto.getPrice().multiply(
        new BigDecimal(productDto.getQuantity() * 3)));
    assertThat(orderDto.getProducts().size()).isEqualTo(1);

  }

}

