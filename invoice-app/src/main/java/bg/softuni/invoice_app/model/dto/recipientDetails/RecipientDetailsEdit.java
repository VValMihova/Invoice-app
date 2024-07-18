package bg.softuni.invoice_app.model.dto.recipientDetails;

import bg.softuni.invoice_app.validation.numeric.Numeric;
import bg.softuni.invoice_app.validation.recipient.annotation.RecipientCompanyEikEditable;
import bg.softuni.invoice_app.validation.recipient.annotation.RecipientCompanyNameEditable;
import bg.softuni.invoice_app.validation.recipient.annotation.NotSameUser;
import bg.softuni.invoice_app.validation.recipient.annotation.RecipientCompanyVatEditable;
import bg.softuni.invoice_app.validation.vatMatchesEik.ValidVatEik;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@ValidVatEik(vatNumber = "vatNumber", eik = "eik")
@RecipientCompanyNameEditable
@RecipientCompanyVatEditable
@RecipientCompanyEikEditable
@NotSameUser
public class RecipientDetailsEdit implements RecipientDetailsProvider {
  private Long id;
  
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.name.length}")
  private String companyName;
  
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.address.length}")
  private String address;
  
  @Numeric
  @NotBlank
  @Size(min = 10, max = 10, message = "{company.details.eik.length}")
  private String eik;
  
  @NotBlank
  @Size(min = 12, max = 12, message = "{company.details.vat.length}")
  private String vatNumber;
  
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.manager.length}")
  private String manager;
  
  public String getAddress() {
    return address;
  }
  
  public RecipientDetailsEdit setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getCompanyName() {
    return companyName;
  }
  
  public RecipientDetailsEdit setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public RecipientDetailsEdit setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public RecipientDetailsEdit setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public RecipientDetailsEdit setManager(String manager) {
    this.manager = manager;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public RecipientDetailsEdit setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
}
