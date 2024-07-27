package bg.softuni.invoice_app.service.companyDetails;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;

public interface CompanyDetailsService {
  
  void addWithRegistration(CompanyDetails companyDetails);
  
  boolean existsByEik(String eik);
  
  boolean existsByCompanyName(String companyName);
  
  boolean existsByVatNumber(String vat);
  
  CompanyDetails update(Long id, CompanyDetailsEditBindingDto companyData);
  
}
