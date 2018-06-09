package com.sda.java.gda.springdemo.service;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sda.java.gda.springdemo.exception.BindingResultException;
import com.sda.java.gda.springdemo.exception.NotFoundException;
import com.sda.java.gda.springdemo.model.Product;
import com.sda.java.gda.springdemo.repository.ProductRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minidev.json.writer.BeansMapper.Bean;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @MockBean
  private ProductRepository productRepository;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private Long id = 1L;
  private Product product;

  @Before
  public void before() {
    product = new Product("name", 10d);
    when(productRepository.findById(id))
        .thenReturn(Optional.of(product));
  }

  @Test
  public void shouldReturnProductById() {
    assertEquals(product, productService.getById(id));
  }

  @Test(expected = NotFoundException.class)
  public void shouldThrowExceptionIfProductDoesNotExists() {
    when(productRepository.findById(any()))
        .thenReturn(Optional.empty());

    productService.getById(23L);
  }

  @Test
  public void shouldThrowExceptionIfProductDoesNotExists2() {
    expectedException.expect(NotFoundException.class);
    expectedException.expectMessage(String.format("Product with id %s not found", id));

    when(productRepository.findById(any()))
        .thenReturn(Optional.empty());

    productService.getById(id);
  }

  @Test
  public void shouldThrowExceptionIfProductDoesNotExists3() {
    when(productRepository.findById(any()))
        .thenReturn(Optional.empty());

    try {
      productService.getById(id);
    } catch (NotFoundException ex) {
      assertEquals(String.format("Product with id %s not found", id), ex.getMessage());
    }
  }

  @Test
  public void shouldFindProducts() {
    when(productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        any(String.class), any(Double.class), any(Double.class)))
        .thenReturn(singletonList(product));
    List<Product> result = productService.search("name", 1d, 10d);
    assertThat(result, hasItems(product));
  }

  @Test
  public void shouldPassEmptyNameIfNull() {
    String empty = "";
    when(productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        eq(empty), any(), any()))
        .thenReturn(singletonList(product));
    List<Product> result = productService.search(null, 1d, 10d);
    assertThat(result, hasItems(product));
    verify(productRepository, times(1))
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        eq(empty), any(), any());
  }

  @Test
  public void shouldFindPageOfProducts() {
    PageRequest pageable = new PageRequest(0, 10);
    Page<Product> page = new PageImpl<>(singletonList(product), pageable, 1);
    when(productRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        any(), any(), any(), eq(pageable)))
        .thenReturn(page);

    Page<Product> result = productService.search(null, 1d, 10d, pageable);

    assertThat(result.getContent(), hasItems(product));
    verify(productRepository, times(1))
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
            any(), any(), any(), eq(pageable));
  }

  @Test
  public void shouldCreateNewProduct() {
    doReturn(product)
        .when(productRepository).save(product);
    doReturn(false)
        .when(productRepository).existsByNameIgnoreCase(any());

    assertEquals(product, productService.create(product, new BeanPropertyBindingResult(null, null)));
  }

  @Test(expected = BindingResultException.class)
  public void shouldNotCreateNewProductIfHasDuplicatedName() {
    doReturn(true)
        .when(productRepository).existsByNameIgnoreCase(any());

    productService.create(product, new BeanPropertyBindingResult(null, null));

    verify(productRepository, never()).save(any());
  }
}
