package bg.softuni.invoice_app.validation.editCompanyDetails;

import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserHelperService;
import bg.softuni.invoice_app.validation.editCompanyDetails.annotation.CompanyVatEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompanyVatEditableValidator implements ConstraintValidator<CompanyVatEditable, String> {
  private final CompanyDetailsService companyDetailsService;
  private final UserHelperService userHelperService;
  
  public CompanyVatEditableValidator(CompanyDetailsService companyDetailsService, UserHelperService userHelperService) {
    this.companyDetailsService = companyDetailsService;
    this.userHelperService = userHelperService;
  }
  
  @Override
  public boolean isValid(String vat, ConstraintValidatorContext constraintValidatorContext) {
    return this.companyDetailsService.getByVatNumber(vat) == null
           || userHelperService.getCompanyDetails().getVatNumber().equals(vat);
  }
}
