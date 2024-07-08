package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.repository.CompanyDetailsRepository;
import bg.softuni.invoice_app.service.CompanyDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService {
  private final CompanyDetailsRepository companyDetailsRepository;
  
  public CompanyDetailsServiceImpl(CompanyDetailsRepository companyDetailsRepository) {
    this.companyDetailsRepository = companyDetailsRepository;
  }
  
  @Override
  public void deleteCompany(Long id) {
    this.companyDetailsRepository.deleteById(id);
    
  }
}
