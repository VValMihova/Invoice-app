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
  
  @Column(nullable = false)
  private Long invoiceNumber;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  public Long getId() {
    return id;
  }
  
  public Sale setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getProductName() {
    return productName;
  }
  
  public Sale setProductName(String productName) {
    this.productName = productName;
    return this;
  }
  
  public BigDecimal getQuantity() {
    return quantity;
  }
  
  public Sale setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }
  
  public LocalDate getSaleDate() {
    return saleDate;
  }
  
  public Sale setSaleDate(LocalDate saleDate) {
    this.saleDate = saleDate;
    return this;
  }
  
  public User getUser() {
    return user;
  }
  
  public Sale setUser(User user) {
    this.user = user;
    return this;
  }
  
  public Long getInvoiceNumber() {
    return invoiceNumber;
  }
  
  public Sale setInvoiceNumber(Long invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
    return this;
  }
}