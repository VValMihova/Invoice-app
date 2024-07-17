package bg.softuni.invoice_app.validation.editCompanyDetails;

import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserHelperService;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.validation.editCompanyDetails.annotation.CompanyNameEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompanyNameEditableValidator implements ConstraintValidator<CompanyNameEditable, String> {
  private final CompanyDetailsService companyDetailsService;
  private final UserService userService;
  
  public CompanyNameEditableValidator(CompanyDetailsService companyDetailsService, UserService userService) {
    this.companyDetailsService = companyDetailsService;
    this.userService = userService;
  }
  
  @Override
  public boolean isValid(String companyName, ConstraintValidatorContext constraintValidatorContext) {
    return this.companyDetailsService.getCompanyByName(companyName) == null
        || userService.showCompanyDetails().getCompanyName().equals(companyName);
  }
}
