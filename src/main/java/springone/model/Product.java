package springone.model;

import java.math.BigDecimal;

public class Product {
  private Long id;
  private String name;
  private String description;
  private Boolean available;
  private BigDecimal price;

  public Product(Long id, String name, String description, Boolean available, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.available = available;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Boolean getAvailable() {
    return available;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
