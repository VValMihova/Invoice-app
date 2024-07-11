package bg.softuni.invoice_app.model.dto.invoice;

import bg.softuni.invoice_app.validation.annotation.SameUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SameUser
public class RecipientDetailsAddDto {
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
  
  
  public String getCompanyName() {
    return companyName;
  }
  
  public RecipientDetailsAddDto setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getAddress() {
    return address;
  }
  
  public RecipientDetailsAddDto setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public RecipientDetailsAddDto setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public RecipientDetailsAddDto setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public RecipientDetailsAddDto setManager(String manager) {
    this.manager = manager;
    return this;
  }
}
