package bg.softuni.invoice_app.model.dto.companyDetails;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GetCompanyDetailsDto {
  private Long id;
  
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
  
  public GetCompanyDetailsDto() {
  }
  
  public String getCompanyName() {
    return companyName;
  }
  
  public GetCompanyDetailsDto setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getAddress() {
    return address;
  }
  
  public GetCompanyDetailsDto setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public GetCompanyDetailsDto setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public GetCompanyDetailsDto setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public GetCompanyDetailsDto setManager(String manager) {
    this.manager = manager;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public GetCompanyDetailsDto setId(Long id) {
    this.id = id;
    return this;
  }
}
