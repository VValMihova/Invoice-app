package bg.softuni.invoice_app.validation.editCompanyDetails;

import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserHelperService;
import bg.softuni.invoice_app.validation.editCompanyDetails.annotation.CompanyNameEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompanyNameEditableValidator implements ConstraintValidator<CompanyNameEditable, String> {
  private final CompanyDetailsService companyDetailsService;
  private final UserHelperService userHelperService;
  
  public CompanyNameEditableValidator(CompanyDetailsService companyDetailsService, UserHelperService userHelperService) {
    this.companyDetailsService = companyDetailsService;
    this.userHelperService = userHelperService;
  }
  
  @Override
  public boolean isValid(String companyName, ConstraintValidatorContext constraintValidatorContext) {
    return this.companyDetailsService.getCompanyByName(companyName) == null
        || userHelperService.getCompanyDetails().getCompanyName().equals(companyName);
  }
}