package bg.softuni.invoice_app.model.dto.sale;

import java.math.BigDecimal;

public class SaleReportDto {
  private String productName;
  private BigDecimal totalQuantity;
  
  public SaleReportDto() {
  }
  
  public SaleReportDto(String productName, BigDecimal totalQuantity) {
    this.productName = productName;
    this.totalQuantity = totalQuantity;
  }
  
  public String getProductName() {
    return productName;
  }
  
  public SaleReportDto setProductName(String productName) {
    this.productName = productName;
    return this;
  }
  
  public BigDecimal getTotalQuantity() {
    return totalQuantity;
  }
  
  public SaleReportDto setTotalQuantity(BigDecimal totalQuantity) {
    this.totalQuantity = totalQuantity;
    return this;
  }
}