package com.sda.java.gda.springdemo.service;

import static java.util.stream.Collectors.toList;

import com.sda.java.gda.springdemo.exception.NotFoundException;
import com.sda.java.gda.springdemo.exception.ValidationException;
import com.sda.java.gda.springdemo.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private List<Book> books = new ArrayList<>();

  public List<Book> search(String name, Integer minPrice, Integer maxPrice) {
    return books.stream()
        .filter(book -> book.getName().contains(name))
        .filter(book -> book.getPrice() >= minPrice)
        .filter(book -> book.getPrice() <= maxPrice)
        .collect(toList());
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
    validate(newBook);
    newBook.setId(++Book.instanceNumber);
    books.add(newBook);
    return newBook;
  }

  public Book update(Book update, Integer id) {
    validate(update);
    Book book = get(id);
    book.setName(update.getName());
    book.setPrice(update.getPrice());
    return book;
  }

  public void remove(Integer id) {
    Book book = get(id);
    books.remove(book);
  }

  private void validate(Book bookToValidate) {
    if (books.stream()
        .anyMatch(book -> book.getName().equals(bookToValidate.getName()))) {
      throw new ValidationException(
          String.format("Book with name %s already exists", bookToValidate.getName()));
    }
  }
}
