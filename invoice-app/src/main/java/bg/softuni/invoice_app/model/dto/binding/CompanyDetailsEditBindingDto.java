package bg.softuni.invoice_app.model.dto.binding;

import bg.softuni.invoice_app.validation.annotation.CompanyEikEditable;
import bg.softuni.invoice_app.validation.annotation.CompanyNameEditable;
import bg.softuni.invoice_app.validation.annotation.CompanyVatEditable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyDetailsEditBindingDto {
  private Long id;
  
  @CompanyNameEditable
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.name.length}")
  private String companyName;

  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.address.length}")
  private String address;
  
  @CompanyEikEditable
  @NotBlank
  @Size(min = 10, max = 10, message = "{company.details.eik.length}")
  private String eik;

  @CompanyVatEditable
  @NotBlank
  @Size(min = 12, max = 12, message = "{company.details.vat.length}")
  private String vatNumber;

  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.manager.length}")
  private String manager;

  public CompanyDetailsEditBindingDto() {
  }

  public String getCompanyName() {
    return companyName;
  }

  public CompanyDetailsEditBindingDto setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  public String getAddress() {
    return address;
  }

  public CompanyDetailsEditBindingDto setAddress(String address) {
    this.address = address;
    return this;
  }

  public String getEik() {
    return eik;
  }

  public CompanyDetailsEditBindingDto setEik(String eik) {
    this.eik = eik;
    return this;
  }

  public String getVatNumber() {
    return vatNumber;
  }

  public CompanyDetailsEditBindingDto setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }

  public String getManager() {
    return manager;
  }

  public CompanyDetailsEditBindingDto setManager(String manager) {
    this.manager = manager;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public CompanyDetailsEditBindingDto setId(Long id) {
    this.id = id;
    return this;
  }
}
