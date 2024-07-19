package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sales")
public class Sale {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String productName;
  
  @Column(nullable = false)
  private BigDecimal quantity;
  
  @Column(nullable = false)
  private LocalDate saleDate;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  public Long getId() {
    return id;
  }
  
  public String getProductName() {
    return productName;
  }
  
  public BigDecimal getQuantity() {
    return quantity;
  }
  
  public LocalDate getSaleDate() {
    return saleDate;
  }
  
  public User getUser() {
    return user;
  }
}