package bg.softuni.invoice_app.validation;

import bg.softuni.invoice_app.model.dto.invoice.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.service.impl.UserHelperService;
import bg.softuni.invoice_app.validation.annotation.SameUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class sameUserDetailsValidator implements ConstraintValidator<SameUser, RecipientDetailsAddDto> {
  
  private final UserHelperService userHelperService;
  
  public sameUserDetailsValidator(UserHelperService userHelperService) {
    this.userHelperService = userHelperService;
  }
  
  @Override
  public boolean isValid(RecipientDetailsAddDto recipientDetailsAddDto, ConstraintValidatorContext constraintValidatorContext) {
    CompanyDetailsEditBindingDto userCompanyDetails = this.userHelperService.getCompanyDetails();
    return
        !userCompanyDetails.getCompanyName().equals(recipientDetailsAddDto.getCompanyName())
        && !userCompanyDetails.getEik().equals(recipientDetailsAddDto.getEik())
        && !userCompanyDetails.getVatNumber().equals(recipientDetailsAddDto.getVatNumber());
  }
}
