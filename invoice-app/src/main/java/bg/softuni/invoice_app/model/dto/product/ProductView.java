package bg.softuni.invoice_app.model.dto.product;

import java.math.BigDecimal;

public class ProductView {
  private Long id;
  private String name;
  private BigDecimal quantity;
  
  public ProductView() {
  }
  
  public Long getId() {
    return id;
  }
  
  public ProductView setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getName() {
    return name;
  }
  
  public ProductView setName(String name) {
    this.name = name;
    return this;
  }
  
  public BigDecimal getQuantity() {
    return quantity;
  }
  
  public ProductView setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }
}
