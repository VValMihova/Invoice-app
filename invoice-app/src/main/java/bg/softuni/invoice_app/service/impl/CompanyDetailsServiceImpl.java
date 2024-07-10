package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.CompanyDetailsRepository;
import bg.softuni.invoice_app.service.CompanyDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService {
  private final CompanyDetailsRepository companyDetailsRepository;
  private final ModelMapper modelMapper;
  
  public CompanyDetailsServiceImpl(CompanyDetailsRepository companyDetailsRepository, ModelMapper modelMapper) {
    this.companyDetailsRepository = companyDetailsRepository;
    this.modelMapper = modelMapper;
  }
  
  @Override
  public void deleteCompany(Long id) {
    this.companyDetailsRepository.deleteById(id);
    
  }
  
  @Override
  public void addWithRegistration(CompanyDetails companyDetails) {
    companyDetailsRepository.save(companyDetails);
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
  
  @Override
  public CompanyDetails getCompanyByName(String companyName) {
    return this.companyDetailsRepository.findByCompanyName(companyName).orElse(null);
  }
  
  @Override
  public void add(CompanyDetailsDto recipient) {
    companyDetailsRepository.save(modelMapper.map(recipient, CompanyDetails.class));
  }
}
