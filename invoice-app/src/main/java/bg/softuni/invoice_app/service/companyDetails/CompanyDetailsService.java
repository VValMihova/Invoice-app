package bg.softuni.invoice_app.service.companyDetails;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;

public interface CompanyDetailsService {
  
  void addWithRegistration(CompanyDetails companyDetails);
  
  CompanyDetails getByEik(String eik);
  
  CompanyDetails getCompanyByName(String companyName);
  
  CompanyDetails getByVatNumber(String vat);
  
  CompanyDetails update(Long id, CompanyDetailsEditBindingDto companyData);
  
}
