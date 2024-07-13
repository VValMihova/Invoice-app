package bg.softuni.invoice_app.validation;

import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserHelperService;
import bg.softuni.invoice_app.validation.annotation.CompanyEikEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompanyEikEditableValidator implements ConstraintValidator<CompanyEikEditable, String> {
  private final CompanyDetailsService companyDetailsService;
  private final UserHelperService userHelperService;
  
  public CompanyEikEditableValidator(CompanyDetailsService companyDetailsService, UserHelperService userHelperService) {
    this.companyDetailsService = companyDetailsService;
    this.userHelperService = userHelperService;
  }
  
  @Override
  public boolean isValid(String eik, ConstraintValidatorContext constraintValidatorContext) {
    return this.companyDetailsService.getByEik(eik) == null
           || userHelperService.getCompanyDetails().getEik().equals(eik);
  }
}
