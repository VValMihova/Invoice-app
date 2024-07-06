package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "invoice_items")
public class InvoiceItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String name;
  
  @Column(nullable = false, precision = 10, scale = 4)
  private BigDecimal quantity;
  
  @Column(nullable = false)
  private BigDecimal unitPrice;
  
  @Column(nullable = false)
  private BigDecimal totalPrice;
  
  public InvoiceItem() {
  }
  
  public Long getId() {
    return id;
  }
  
  public InvoiceItem setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getName() {
    return name;
  }
  
  public InvoiceItem setName(String name) {
    this.name = name;
    return this;
  }
  
  public BigDecimal getQuantity() {
    return quantity;
  }
  
  public InvoiceItem setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }
  
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }
  
  public InvoiceItem setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }
  
  public BigDecimal getTotalPrice() {
    return totalPrice;
  }
  
  public InvoiceItem setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }
  
  @PrePersist
  @PreUpdate
  private void calculateTotalPrice() {
    this.totalPrice = this.unitPrice.multiply(quantity);
  }
  
  private double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();
    
    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
