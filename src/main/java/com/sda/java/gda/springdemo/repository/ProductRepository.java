package com.sda.java.gda.springdemo.repository;

import com.sda.java.gda.springdemo.model.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Boolean existsByNameIgnoreCase(String name);

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

  @Query(value = "SELECT * FROM products WHERE name = :name", nativeQuery = true)
  List<Product> search(@Param("name") String nameParam);
}
