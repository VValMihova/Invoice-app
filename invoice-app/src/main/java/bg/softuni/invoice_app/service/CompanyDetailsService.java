package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;

import java.util.Optional;

public interface CompanyDetailsService {
  void deleteCompany(Long id);
  void addWithRegistration(CompanyDetails companyDetails);
  CompanyDetails getByEik(String eik);
  boolean exists(CompanyDetails companyDetails);
  
  void save(CompanyDetails companyDetails);
  
  CompanyDetails getCompanyByName(String companyName);
  
 // void add(CompanyDetailsDto recipient);
  
  CompanyDetails update(Long id, CompanyDetailsEditBindingDto companyData);
  
  CompanyDetails getByVatNumber(String vat);
}
