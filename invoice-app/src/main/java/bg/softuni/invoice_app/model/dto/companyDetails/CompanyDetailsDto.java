package bg.softuni.invoice_app.model.dto.companyDetails;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyDetailsDto {
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.name.length}")
  private String companyName;
  
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.address.length}")
  private String address;
  
  @NotBlank
  @Size(min = 10, max = 10, message = "{company.details.eik.length}")
  private String eik;
  
  @NotBlank
  @Size(min = 12, max = 12, message = "{company.details.vat.length}")
  private String vatNumber;
  
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.manager.length}")
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
