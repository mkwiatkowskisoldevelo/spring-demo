package com.sda.java.gda.springdemo.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "receipts")
public class Receipt extends BaseEntity {

  @Column(nullable = false)
  private String buyer;

  @Column(nullable = false)
  private LocalDateTime date;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(name = "receipt_products",
      joinColumns =
      @JoinColumn(name = "receipt_id", nullable = false),
      inverseJoinColumns =
      @JoinColumn(name = "product_id", nullable = false))
  private List<Product> products;
}
