package bg.softuni.invoice_app.validation.editCompanyDetails;

import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.validation.editCompanyDetails.annotation.CompanyVatEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompanyVatEditableValidator implements ConstraintValidator<CompanyVatEditable, String> {
  private final CompanyDetailsService companyDetailsService;
  private final UserService userService;
  
  public CompanyVatEditableValidator(CompanyDetailsService companyDetailsService, UserService userService) {
    this.companyDetailsService = companyDetailsService;
    this.userService = userService;
  }
  
  @Override
  public boolean isValid(String vat, ConstraintValidatorContext constraintValidatorContext) {
    return this.companyDetailsService.getByVatNumber(vat) == null
           || userService.showCompanyDetails().getVatNumber().equals(vat);
  }
}
