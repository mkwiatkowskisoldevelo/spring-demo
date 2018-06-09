package com.sda.java.gda.springdemo.controller;

import com.sda.java.gda.springdemo.service.CounterComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @Autowired
  private CounterComponent counterComponent;

  private List<String> list = new ArrayList<>();

  /*@RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<String> heloWorld() {
    return ResponseEntity.ok("Hello World!");
  }*/

  @GetMapping("/hello")
  @ResponseStatus(HttpStatus.OK)
  public String hello() {
    return "Hello World!";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ResponseEntity<String> add(@RequestBody String body) {
    String lowerCased = body.toLowerCase();
    list.add(lowerCased);
    return ResponseEntity.ok(lowerCased);
  }

  @RequestMapping(value = "/getAll", method = RequestMethod.GET)
  public ResponseEntity<List<String>> getAll() {
    return ResponseEntity.ok(list);
  }

  @RequestMapping(value = "/find", method = RequestMethod.GET)
  public ResponseEntity<List<String>> find(
      @RequestParam(value="filter", defaultValue = "") String filtr) {
    if (filtr.length() < 3) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(list.stream()
        .filter(string -> string.contains(filtr))
        .collect(Collectors.toList()));
  }

  @GetMapping("/count")
  @ResponseStatus(HttpStatus.OK)
  public int count() {
    return counterComponent.increase();
  }
}
