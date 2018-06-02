package com.sda.java.gda.springdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

  public static Integer instanceNumber = 0;

  private Integer id;
  private String name;
  private Integer price;
}
