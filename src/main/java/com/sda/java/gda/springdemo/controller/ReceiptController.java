package com.sda.java.gda.springdemo.controller;

import com.sda.java.gda.springdemo.exception.NotFoundException;
import com.sda.java.gda.springdemo.model.Product;
import com.sda.java.gda.springdemo.model.Receipt;
import com.sda.java.gda.springdemo.repository.ProductRepository;
import com.sda.java.gda.springdemo.repository.ReceiptRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

  @Autowired
  private ReceiptRepository receiptRepository;

  @Autowired
  private ProductRepository productRepository;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Receipt> search(
      @RequestParam(defaultValue = "") String buyer,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam(required = false) Long productId) {

    if (null == startDate) {
      startDate = LocalDate.MIN;
    }
    if (null == endDate) {
      endDate = LocalDate.MAX;
    }
    Product product = null != productId
            ? productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %s not found", productId)))
            : null;

    return receiptRepository.findByBuyerIgnoreCaseContainingAndDateBetweenAndProductsIsContaining(
        buyer,
        LocalDateTime.of(startDate, LocalTime.MIN),
        LocalDateTime.of(endDate, LocalTime.MAX),
        product
    );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Receipt create(@RequestBody Receipt receipt) {
    return receiptRepository.save(receipt);
  }

  @GetMapping("/summary")
  @ResponseStatus(HttpStatus.OK)
  public Double summary() {
    List<Receipt> receipts = receiptRepository.findAll();

    Double sum = 0d;
    for (Receipt receipt : receipts) {
      sum = sum + sum(receipt);
    }
    return sum;
  }

  @GetMapping("/{id}/summary")
  @ResponseStatus(HttpStatus.OK)
  public Double receiptSummary(@PathVariable Long id) {
    Optional<Receipt> receipt = receiptRepository.findById(id);

    if (receipt.isPresent()) {
      return sum(receipt.get());
    }

    return 0d;
  }

  private Double sum(Receipt receipt) {
    Double receiptSum = 0d;
    for (Product product : receipt.getProducts()) {
      receiptSum = receiptSum + product.getPrice();
    }
    return receiptSum;
  }
}
