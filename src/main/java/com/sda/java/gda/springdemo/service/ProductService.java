package com.sda.java.gda.springdemo.service;

import com.sda.java.gda.springdemo.exception.BindingResultException;
import com.sda.java.gda.springdemo.exception.NotFoundException;
import com.sda.java.gda.springdemo.model.Product;
import com.sda.java.gda.springdemo.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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

  public List<Product> search(String name, Double minPrice, Double maxPrice) {
    if (null == name) {
      name = "";
    }
    return productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        name, minPrice, maxPrice);
  }

  public Page<Product> search(String name, Double minPrice, Double maxPrice, Pageable pageable) {
    return productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        name, minPrice, maxPrice, pageable);
  }

  public Product update(Product product, Long id, BindingResult bindingResult) {
    Product existingProduct = getById(id);
    validate(product, existingProduct.getName(), bindingResult);
    existingProduct.setName(product.getName());
    existingProduct.setPrice(product.getPrice());
    return productRepository.save(existingProduct);
  }

  public Product create(Product product, BindingResult bindingResult) {
    validate(product, null, bindingResult);
    return productRepository.save(product);
  }

  private void validate(Product product, String currentName,
      BindingResult bindingResult) {
    if (!product.getName().equalsIgnoreCase(currentName)
        && productRepository.existsByNameIgnoreCase(product.getName())) {
      bindingResult.addError(
          new FieldError("product", "name",
              String.format("Product with name %s already exists", product.getName())));
    }
    if (bindingResult.hasErrors()) {
      throw new BindingResultException(bindingResult);
    }
  }
}
