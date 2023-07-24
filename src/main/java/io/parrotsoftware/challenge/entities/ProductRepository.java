package io.parrotsoftware.challenge.entities;

import io.parrotsoftware.challenge.dtos.ProductReportItem;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>{

  @Query(nativeQuery = true)
  List<ProductReportItem> getProductsReport(Date from, Date to);

}


