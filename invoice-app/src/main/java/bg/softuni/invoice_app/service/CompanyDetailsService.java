package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;

public interface CompanyDetailsService {
  void deleteCompany(Long id);
  void add(CompanyDetailsDto companyDetailsDto);
  CompanyDetails getByEik(String eik);
  boolean exists(CompanyDetails companyDetails);
  
  void save(CompanyDetails companyDetails);
}
