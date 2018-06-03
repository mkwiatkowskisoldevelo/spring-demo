package com.sda.java.gda.springdemo.controller;

import com.sda.java.gda.springdemo.model.Book;
import com.sda.java.gda.springdemo.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/books")
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Book> search(
      @RequestParam(defaultValue = "") String name,
      @RequestParam(defaultValue = "0") Integer minPrice,
      @RequestParam(required = false) Integer maxPrice) {
    if (null == maxPrice) {
      maxPrice = Integer.MAX_VALUE;
    }
    return bookService.search(name, minPrice, maxPrice);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book get(@PathVariable Integer id) {
    return bookService.get(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Book create(@RequestBody Book newBook) {
    return bookService.create(newBook);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book update(@RequestBody Book book, @PathVariable Integer id) {
    return bookService.update(book, id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remove(@PathVariable Integer id) {
    bookService.remove(id);
  }
}
