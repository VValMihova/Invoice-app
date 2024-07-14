package bg.softuni.invoice_app.model.dto.recipientDetails;

import bg.softuni.invoice_app.model.entity.RecipientDetails;

public class RecipientDetailsView {
  private Long id;
  private String companyName;
  private String address;
  private String eik;
  private String vatNumber;
  private String manager;
  
  public RecipientDetailsView() {
  }
  
  public RecipientDetailsView(RecipientDetails recipientDetails) {
    this.id = recipientDetails.getId();
    this.companyName = recipientDetails.getCompanyName();
    this.address = recipientDetails.getAddress();
    this.eik = recipientDetails.getEik();
    this.vatNumber = recipientDetails.getVatNumber();
    this.manager = recipientDetails.getManager();
  }
  
  public String getAddress() {
    return address;
  }
  
  public RecipientDetailsView setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getCompanyName() {
    return companyName;
  }
  
  public RecipientDetailsView setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public RecipientDetailsView setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public RecipientDetailsView setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public RecipientDetailsView setManager(String manager) {
    this.manager = manager;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public RecipientDetailsView setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
}
