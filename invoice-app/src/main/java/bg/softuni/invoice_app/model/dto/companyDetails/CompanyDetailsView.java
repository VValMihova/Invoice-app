package bg.softuni.invoice_app.model.dto.companyDetails;

import bg.softuni.invoice_app.model.entity.CompanyDetails;

public class CompanyDetailsView {
  private Long id;
  private String companyName;
  private String address;
  private String eik;
  private String vatNumber;
  private String manager;
  
  public CompanyDetailsView() {
  }
  
  public CompanyDetailsView(CompanyDetails companyDetails) {
    this.id = companyDetails.getId();
    this.companyName = companyDetails.getCompanyName();
    this.address = companyDetails.getAddress();
    this.eik = companyDetails.getEik();
    this.vatNumber = companyDetails.getVatNumber();
    this.manager = companyDetails.getManager();
  }
  
  public String getAddress() {
    return address;
  }
  
  public CompanyDetailsView setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getCompanyName() {
    return companyName;
  }
  
  public CompanyDetailsView setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public CompanyDetailsView setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public CompanyDetailsView setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public CompanyDetailsView setManager(String manager) {
    this.manager = manager;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public CompanyDetailsView setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
}
