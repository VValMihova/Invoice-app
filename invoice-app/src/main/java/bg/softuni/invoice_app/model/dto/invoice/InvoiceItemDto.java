package bg.softuni.invoice_app.model.dto.invoice;

import java.math.BigDecimal;

public class InvoiceItemDto {
  private String name;
  private BigDecimal quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
  
  public String getName() {
    return name;
  }
  
  public InvoiceItemDto setName(String name) {
    this.name = name;
    return this;
  }
  
  public BigDecimal getQuantity() {
    return quantity;
  }
  
  public InvoiceItemDto setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }
  
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }
  
  public InvoiceItemDto setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }
  
  public BigDecimal getTotalPrice() {
    return totalPrice;
  }
  
  public InvoiceItemDto setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }
}
