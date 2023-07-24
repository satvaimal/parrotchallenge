package io.parrotsoftware.challenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.parrotsoftware.challenge.dtos.ProductReportItem;
import io.parrotsoftware.challenge.entities.ProductRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerGetProductsReportTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductRepository productRepository;
 
  @Test
  public void reportSuccessfullyObtained() throws Exception {

    List<ProductReportItem> items = Mocks.mockProductReportItems();
    when(productRepository.getProductsReport(
        any(Date.class), any(Date.class))).thenReturn(items);
    this.mockMvc.perform(get(
        "/products_report?from=2023-07-01&to=2023-07-31")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("Cookies")))
        .andExpect(jsonPath("$[0].quantity", is(5)))
        .andExpect(jsonPath("$[0].total", is(10)));

  }

  @Test
  public void queryParameterIsMissing() throws Exception {

    List<ProductReportItem> items = Mocks.mockProductReportItems();
    this.mockMvc.perform(get(
        "/products_report?from=2023-07-01")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[0].field", is("to")))
        .andExpect(jsonPath("$.errors[0].message", is("Parameter is required")));

  }

  @Test
  public void queryParameterIsWrong() throws Exception {

    List<ProductReportItem> items = Mocks.mockProductReportItems();
    this.mockMvc.perform(get(
        "/products_report?from=2023-07-01&to=2023-07-")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[0].field", is("to")))
        .andExpect(jsonPath("$.errors[0].message", is("Parameter type mismatch")));

  }

  @Test
  public void dateRangeIsWrong() throws Exception {

    List<ProductReportItem> items = Mocks.mockProductReportItems();
    this.mockMvc.perform(get(
        "/products_report?from=2023-07-31&to=2023-07-01")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[0].field", is("from")))
        .andExpect(jsonPath("$.errors[0].message", is(
            "'from' date is greater or equal than 'to' date")));

  }

}

