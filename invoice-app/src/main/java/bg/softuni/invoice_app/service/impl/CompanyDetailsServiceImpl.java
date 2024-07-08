package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
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
  
  @Override
  public void add(CompanyDetailsDto companyDetailsDto) {
    companyDetailsRepository.save(new CompanyDetails(companyDetailsDto));
  }
  
  @Override
  public CompanyDetails getByEik(String eik) {
    return this.companyDetailsRepository.findByEik(eik).orElse(null);
  }
  
  @Override
  public boolean exists(CompanyDetails companyDetails) {
    return this.companyDetailsRepository.findByEik(companyDetails.getEik()).isPresent();
  }
  
  @Override
  public void save(CompanyDetails companyDetails) {
    this.companyDetailsRepository.save(companyDetails);
  }
}
