package bg.softuni.invoice_app.validation.editCompanyDetails;

import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.validation.editCompanyDetails.annotation.CompanyEikEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompanyEikEditableValidator implements ConstraintValidator<CompanyEikEditable, String> {
  private final CompanyDetailsService companyDetailsService;
  private final UserService userService;
  
  public CompanyEikEditableValidator(CompanyDetailsService companyDetailsService, UserService userService) {
    this.companyDetailsService = companyDetailsService;
    this.userService = userService;
  }
  
  @Override
  public boolean isValid(String eik, ConstraintValidatorContext constraintValidatorContext) {
    return this.companyDetailsService.getByEik(eik) == null
           || userService.showCompanyDetails().getEik().equals(eik);
  }
}
