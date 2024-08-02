package bg.softuni.invoice_app.service.companyDetails;

import bg.softuni.invoice_app.exeption.CompanyNotFoundException;
import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.CompanyDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService {
  private final CompanyDetailsRepository companyDetailsRepository;
  
  public CompanyDetailsServiceImpl(
      CompanyDetailsRepository companyDetailsRepository) {
    this.companyDetailsRepository = companyDetailsRepository;
  }
  
  @Override
  @Transactional
  public void addWithRegistration(CompanyDetails companyDetails) {
    if (companyDetails == null) {
      return;
    }
    companyDetailsRepository.save(companyDetails);
  }
  
  @Override
  public boolean existsByEik(String eik) {
    return this.companyDetailsRepository.existsByEik(eik);
  }
  
  @Override
  public boolean existsByCompanyName(String companyName) {
    return this.companyDetailsRepository.existsByCompanyName(companyName);
  }
  
  @Override
  public boolean existsByVatNumber(String vat) {
    return this.companyDetailsRepository.existsByVatNumber(vat);
  }
  
  @Override
  public CompanyDetails update(Long id, CompanyDetailsEditBindingDto companyData) {
    CompanyDetails existingCompany = this.companyDetailsRepository.findById(id)
        .orElseThrow(() -> new CompanyNotFoundException(ErrorMessages.COMPANY_NOT_FOUND));
    
    existingCompany.setCompanyName(companyData.getCompanyName())
        .setAddress(companyData.getAddress())
        .setEik(companyData.getEik())
        .setVatNumber(companyData.getVatNumber())
        .setManager(companyData.getManager());
    
    return this.companyDetailsRepository.save(existingCompany);
  }
  
  
}
