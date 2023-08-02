package io.parrotsoftware.challenge.controllers;

import io.parrotsoftware.challenge.dtos.OrderDto;
import io.parrotsoftware.challenge.services.OrderService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/orders")
  public ResponseEntity<OrderDto> create(@Valid @RequestBody OrderDto order) {
    OrderDto newOrder = orderService.processOrder(order);
    return new ResponseEntity<OrderDto>(newOrder, HttpStatus.CREATED);
  }

}

