package bg.softuni.invoice_app.validation.vallidation;

import bg.softuni.invoice_app.service.CompanyDetailsService;
import bg.softuni.invoice_app.validation.vallidation.annotation.UniqueCompanyName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueCompanyNameValidator implements ConstraintValidator<UniqueCompanyName, String> {
  private final CompanyDetailsService companyDetailsService;
  
  public UniqueCompanyNameValidator(CompanyDetailsService companyDetailsService) {
    this.companyDetailsService = companyDetailsService;
  }
  
  @Override
  public boolean isValid(String companyName, ConstraintValidatorContext constraintValidatorContext) {
    return this.companyDetailsService.getCompanyByName(companyName) == null;
  }
}
