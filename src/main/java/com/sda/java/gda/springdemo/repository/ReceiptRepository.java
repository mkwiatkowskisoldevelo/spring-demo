package com.sda.java.gda.springdemo.repository;

import com.sda.java.gda.springdemo.model.Product;
import com.sda.java.gda.springdemo.model.Receipt;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

  List<Receipt> findByBuyerIgnoreCaseContainingAndDateBetween(
      String buyer,
      LocalDateTime startDate,
      LocalDateTime endDate);

  @Query(value = "SELECT * FROM receipts r "
      + "JOIN receipt_products rp ON r.id = rp.receipt_id "
      + "JOIN products p ON rp.product_id = p.id "
      + "WHERE p.name = :name", nativeQuery = true)
  List<Receipt> search(@Param("name") String productName);
}
