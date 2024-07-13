package bg.softuni.invoice_app.model.dto.invoice;

import bg.softuni.invoice_app.model.entity.InvoiceItem;

import java.math.BigDecimal;

public class InvoiceItemView {
  private Long id;
  private String name;
  private BigDecimal quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
  
  public InvoiceItemView(InvoiceItem invoiceItem) {
    this.id = invoiceItem.getId();
    this.name = invoiceItem.getName();
    this.quantity = invoiceItem.getQuantity();
    this.unitPrice = invoiceItem.getUnitPrice();
    this.totalPrice = invoiceItem.getTotalPrice();
  }
  
  public Long getId() {
    return id;
  }
  
  public InvoiceItemView setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getName() {
    return name;
  }
  
  public InvoiceItemView setName(String name) {
    this.name = name;
    return this;
  }
  
  public BigDecimal getQuantity() {
    return quantity;
  }
  
  public InvoiceItemView setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }
  
  public BigDecimal getTotalPrice() {
    return totalPrice;
  }
  
  public InvoiceItemView setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }
  
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }
  
  public InvoiceItemView setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }
}
