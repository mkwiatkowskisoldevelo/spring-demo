package com.sda.java.gda.springdemo.service;

import com.sda.java.gda.springdemo.model.Book;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private List<Book> books = new ArrayList<>();

  public List<Book> search() {
    return books;
  }

  public Book create(Book newBook) {
    newBook.setId(++Book.instanceNumber);
    books.add(newBook);
    return newBook;
  }
}
