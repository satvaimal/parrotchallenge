package io.parrotsoftware.challenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.parrotsoftware.challenge.dtos.ProductDto;
import io.parrotsoftware.challenge.dtos.OrderDto;
import io.parrotsoftware.challenge.services.OrderService;
import io.parrotsoftware.challenge.utils.Mocks;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerCreateTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private OrderService orderService;
 
  @Test
  public void orderSuccessfullyCreated() throws Exception {

    OrderDto order = Mocks.mockOrderDto();
    ProductDto product = order.getProducts().get(0);
    when(orderService.processOrder(any(OrderDto.class))).thenReturn(order);
    this.mockMvc.perform(post("/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(order)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.clientName", is(order.getClientName())))
        .andExpect(jsonPath("$.products[0].name", is(product.getName())))
        .andExpect(jsonPath("$.products[0].price", is(product.getPrice())))
        .andExpect(jsonPath("$.products[0].quantity", is(product.getQuantity())));

  }

  @Test
  public void orderIsInvalid() throws Exception {

    OrderDto order = Mocks.mockOrderDto();
    order.setProducts(null);
    when(orderService.processOrder(any(OrderDto.class))).thenReturn(order);
    this.mockMvc.perform(post("/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(order)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[0].field", is("products")))
        .andExpect(jsonPath("$.errors[0].message", is("Product list is empty")));

  }

}

