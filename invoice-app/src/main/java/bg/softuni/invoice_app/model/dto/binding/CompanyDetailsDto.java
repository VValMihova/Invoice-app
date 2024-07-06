package bg.softuni.invoice_app.model.dto.binding;

import jakarta.persistence.Column;

public class CompanyDetailsDto {
  private String companyName;
  
  private String address;
  
  private String eik;
  
  private String vatNumber;
  
  private String manager;
  
  public CompanyDetailsDto() {
  }
  
  public String getCompanyName() {
    return companyName;
  }
  
  public CompanyDetailsDto setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getAddress() {
    return address;
  }
  
  public CompanyDetailsDto setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public CompanyDetailsDto setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public CompanyDetailsDto setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public CompanyDetailsDto setManager(String manager) {
    this.manager = manager;
    return this;
  }
}
