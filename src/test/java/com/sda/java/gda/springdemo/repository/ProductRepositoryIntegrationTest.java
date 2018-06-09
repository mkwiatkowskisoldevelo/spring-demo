package com.sda.java.gda.springdemo.repository;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.sda.java.gda.springdemo.model.Product;
import com.sda.java.gda.springdemo.model.Receipt;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryIntegrationTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ReceiptRepository receiptRepository;

  private Product product;

  @Before
  public void setUp() {
    product = productRepository.save(new Product("name", 10d));
  }

  @Test
  public void shoudlFindProductById() {
    assertEquals(product, productRepository.findById(product.getId()).get());
  }

  @Test
  public void shouldFindByName() {
    Product product1 = productRepository.save(new Product("NAME-asd", 10d));
    Product product2 = productRepository.save(new Product("asd-nAmE", 10d));
    Product product3 = productRepository.save(new Product("some-name", 10d));

    List<Product> result = productRepository.findByNameContainingIgnoreCase("name");

    assertThat(result, hasItems(product, product1, product2, product3));
  }

  @Test
  public void shouldFindPageOfProductsByNameAndPrice() {
    Product product1 = productRepository.save(new Product("NAME-asd", 1d));
    Product product2 = productRepository.save(new Product("some-name", 10d));
    Product product3 = productRepository.save(new Product("asd-nAmE", 11d));
    Product product4 = productRepository.save(new Product("aaaaa", 12d));
    Product product5 = productRepository.save(new Product("ascascasc-name", 100d));
    Pageable pageable = new PageRequest(1, 1, new Sort("name"));

    Page<Product> result = productRepository
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
        "name", 10d, 20d, pageable);

    assertThat(result.getContent(), hasItems(product));
  }

  @Test
  public void shouldFindProductsByName() {
    assertEquals(0, productRepository.search("asdasdasda").size());
    assertThat(productRepository.search("name"), hasItem(product));
  }

  @Test
  public void shouldFindReceiptsByName() {
    Receipt receipt = receiptRepository.save(new Receipt("Tomek", LocalDateTime.now(), Collections.singletonList(product)));
    assertThat(receiptRepository.search("name"), hasItem(receipt));
  }
}
