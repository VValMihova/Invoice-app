package bg.softuni.invoice_app.model.entity;

import bg.softuni.invoice_app.model.dto.invoice.InvoiceItemDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, unique = true)
  private String name;
  
  @Column(nullable = false)
  private BigDecimal quantity;
  
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  
  public Product() {
    this.quantity = BigDecimal.ZERO;
  }
  public Product(InvoiceItemDto item) {
    this.name = item.getName();
  }
  
  public Long getId() {
    return id;
  }
  
  public Product setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getName() {
    return name;
  }
  
  public Product setName(String name) {
    this.name = name;
    return this;
  }
  
  public User getUser() {
    return user;
  }
  
  public Product setUser(User user) {
    this.user = user;
    return this;
  }
  
  public BigDecimal getQuantity() {
    return quantity;
  }
  
  public Product setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }
}
