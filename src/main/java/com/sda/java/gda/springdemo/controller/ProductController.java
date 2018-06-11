package com.sda.java.gda.springdemo.controller;

import com.sda.java.gda.springdemo.exception.BindingResultException;
import com.sda.java.gda.springdemo.exception.ValidationException;
import com.sda.java.gda.springdemo.model.Product;
import com.sda.java.gda.springdemo.repository.ProductRepository;
import com.sda.java.gda.springdemo.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(
      @RequestBody @Valid Product product,
      BindingResult bindingResult) {
    return productService.create(product, bindingResult);
  }

  /*@GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<Product> search(
      @RequestParam(defaultValue = "") String name,
      @RequestParam(defaultValue = "0") Double minPrice,
      @RequestParam(required = false) Double maxPrice,
      Pageable pageable) {
    if (maxPrice == null) {
      maxPrice = Double.MAX_VALUE;
    }
    return productService.search(name, minPrice, maxPrice, pageable);
  }*/

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Product> search() {
    return productRepository.findAll();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long productId) {
    productService.delete(productId);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product get(@PathVariable Long id) {
    return productService.getById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product update(
      @PathVariable Long id,
      @Valid @RequestBody Product product,
      BindingResult bindingResult) {
    return productService.update(product, id, bindingResult);
  }
}
