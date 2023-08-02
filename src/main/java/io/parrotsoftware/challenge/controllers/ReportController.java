package io.parrotsoftware.challenge.controllers;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import io.parrotsoftware.challenge.dtos.ProductReportItem;
import io.parrotsoftware.challenge.entities.ProductRepository;
import io.parrotsoftware.challenge.errors.MyError;
import io.parrotsoftware.challenge.errors.MyValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

  @Autowired
  ProductRepository productRepository;

  @GetMapping("/products_report")
  public ResponseEntity<List<ProductReportItem>> getProductsReport(
      @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
      @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {

    if (from.compareTo(to) > 0) {
      throw new MyValidationException(new MyError(
          "from", "'from' date is greater or equal than 'to' date"));
    }

    return new ResponseEntity<List<ProductReportItem>>(
        productRepository.getProductsReport(from, to), HttpStatus.OK);

  }

}

