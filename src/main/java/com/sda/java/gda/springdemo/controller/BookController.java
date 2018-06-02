package com.sda.java.gda.springdemo.controller;

import com.sda.java.gda.springdemo.model.Book;
import com.sda.java.gda.springdemo.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/books")
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Book> search(@RequestParam(defaultValue = "0x80000000") Integer minPrice) {
    return bookService.search();
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


}
