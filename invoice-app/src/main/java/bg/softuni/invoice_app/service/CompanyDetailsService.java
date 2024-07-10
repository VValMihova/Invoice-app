package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;

public interface CompanyDetailsService {
  void deleteCompany(Long id);
  void addWithRegistration(CompanyDetails companyDetails);
  CompanyDetails getByEik(String eik);
  boolean exists(CompanyDetails companyDetails);
  
  CompanyDetails saveAndReturn(CompanyDetails companyDetails);
  
  CompanyDetails getCompanyByName(String companyName);
  
  
  CompanyDetails update(Long id, CompanyDetailsEditBindingDto companyData);
  
  CompanyDetails getByVatNumber(String vat);
}
