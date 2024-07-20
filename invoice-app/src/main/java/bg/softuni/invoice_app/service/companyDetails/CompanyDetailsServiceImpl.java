package bg.softuni.invoice_app.service.companyDetails;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.CompanyDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService {
  private final CompanyDetailsRepository companyDetailsRepository;
  
  public CompanyDetailsServiceImpl(
      CompanyDetailsRepository companyDetailsRepository) {
    this.companyDetailsRepository = companyDetailsRepository;
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
  public CompanyDetails getByName(String companyName) {
    return this.companyDetailsRepository.findByCompanyName(companyName).orElse(null);
  }
  
  @Override
  public CompanyDetails getByVatNumber(String vat) {
    return this.companyDetailsRepository.findByVatNumber(vat).orElse(null);
  }
  
  @Override
  public CompanyDetails update(Long id, CompanyDetailsEditBindingDto companyData) {
    CompanyDetails existingCompany = this.companyDetailsRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Company"));
    
    existingCompany.setCompanyName(companyData.getCompanyName())
        .setAddress(companyData.getAddress())
        .setEik(companyData.getEik())
        .setVatNumber(companyData.getVatNumber())
        .setManager(companyData.getManager());
    
    return this.companyDetailsRepository.save(existingCompany);
  }
  
  
}
