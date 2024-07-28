package bg.softuni.invoice_app.model.dto.invoice;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class InvoiceItemDto {
  @NotNull(message = "{invoice.item.name.not.null}")
  @Size(min = 2, max = 20, message = "{invoice.item.name.length}")
  private String name;
  
  @NotNull(message = "{invoice.item.not.null}")
  @Positive(message = "{invoice.item.positive}")
  private BigDecimal quantity;
  
  @NotNull(message = "{invoice.item.not.null}")
  @Positive(message = "{invoice.item.positive}")
  private BigDecimal unitPrice;
  
  @NotNull(message = "{invoice.item.not.null}")
  @Positive(message = "{invoice.item.positive}")
  private BigDecimal totalPrice;
  
  public InvoiceItemDto() {
  }
  public InvoiceItemDto(InvoiceItemView itemView) {
    this.name = itemView.getName();
    this.quantity = itemView.getQuantity();
    this.unitPrice = itemView.getUnitPrice();
    this.totalPrice = itemView.getTotalPrice();
  }
  
  
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
