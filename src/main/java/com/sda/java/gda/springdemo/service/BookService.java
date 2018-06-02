package com.sda.java.gda.springdemo.service;

import com.sda.java.gda.springdemo.exception.NotFoundException;
import com.sda.java.gda.springdemo.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private List<Book> books = new ArrayList<>();

  public List<Book> search() {
    return books;
  }

  public Book get(Integer id) {
    Optional<Book> foundBook = books.stream()
        .filter(book -> book.getId().equals(id))
        .findFirst();

    if (!foundBook.isPresent()) {
      throw new NotFoundException(String.format("book with id %s not found", id));
    }
    return foundBook.get();
  }

  public Book create(Book newBook) {
    newBook.setId(++Book.instanceNumber);
    books.add(newBook);
    return newBook;
  }
}
