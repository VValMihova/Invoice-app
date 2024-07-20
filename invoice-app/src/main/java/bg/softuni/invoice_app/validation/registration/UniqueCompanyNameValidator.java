package bg.softuni.invoice_app.validation.registration;

import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.validation.registration.annotation.UniqueCompanyName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueCompanyNameValidator implements ConstraintValidator<UniqueCompanyName, String> {
  private final CompanyDetailsService companyDetailsService;
  
  public UniqueCompanyNameValidator(CompanyDetailsService companyDetailsService) {
    this.companyDetailsService = companyDetailsService;
  }
  
  @Override
  public boolean isValid(String companyName, ConstraintValidatorContext constraintValidatorContext) {
    if (companyName == null) {
      return true;
    }
    
    return this.companyDetailsService.getByName(companyName) == null;
  }
}
