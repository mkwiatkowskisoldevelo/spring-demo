package com.sda.java.gda.springdemo.service;

import com.sda.java.gda.springdemo.exception.NotFoundException;
import com.sda.java.gda.springdemo.model.Product;
import com.sda.java.gda.springdemo.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public void delete(Long id) {
    if (!productRepository.existsById(id)) {
      throw new NotFoundException(String.format("Product with id %s not found", id));
    }
    productRepository.deleteById(id);
  }

  public Product getById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    if (!product.isPresent()) {
      throw new NotFoundException(String.format("Product with id %s not found", id));
    }
    return product.get();
  }

  public Product update(Product product, Long id) {
    Product existingProduct = getById(id);
    existingProduct.setName(product.getName());
    existingProduct.setPrice(product.getPrice());
    return productRepository.save(existingProduct);
  }

  public List<Product> search(String name, Double minPrice, Double maxPrice) {
    return productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        name, minPrice, maxPrice);
  }

  public Page<Product> search(String name, Double minPrice, Double maxPrice, Pageable pageable) {
    return productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        name, minPrice, maxPrice, pageable);
  }
}
