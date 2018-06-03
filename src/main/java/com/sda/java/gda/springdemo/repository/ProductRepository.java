package com.sda.java.gda.springdemo.repository;

import com.sda.java.gda.springdemo.model.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByName(String productName);
  List<Product> findByNameContaining(String productName);
  List<Product> findByNameContainingIgnoreCase(String productName);

  List<Product>
  findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
      String productName,
      Double minPrice,
      Double maxPrice);

  Page<Product>
  findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
      String productName,
      Double minPrice,
      Double maxPrice,
      Pageable pageable);
}
