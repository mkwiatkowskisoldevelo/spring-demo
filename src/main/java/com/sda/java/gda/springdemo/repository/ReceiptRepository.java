package com.sda.java.gda.springdemo.repository;

import com.sda.java.gda.springdemo.model.Product;
import com.sda.java.gda.springdemo.model.Receipt;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

  List<Receipt> findByBuyerIgnoreCaseContainingAndDateBetween(
      String buyer,
      LocalDateTime startDate,
      LocalDateTime endDate);
}
