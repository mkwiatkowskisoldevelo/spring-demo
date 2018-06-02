package com.sda.java.gda.springdemo.controller;

import com.sda.java.gda.springdemo.exception.NotFoundException;
import com.sda.java.gda.springdemo.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerHandlerException {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public String notFoundExceptionHandler(NotFoundException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String validationExceptionHandler(ValidationException ex) {
    return ex.getMessage();
  }
}
