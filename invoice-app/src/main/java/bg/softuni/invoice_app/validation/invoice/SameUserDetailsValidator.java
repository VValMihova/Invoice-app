package bg.softuni.invoice_app.validation.invoice;

import bg.softuni.invoice_app.model.dto.invoice.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.service.user.UserHelperService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SameUserDetailsValidator implements ConstraintValidator<SameUser, RecipientDetailsAddDto> {
  
  private final UserHelperService userHelperService;
  
  public SameUserDetailsValidator(UserHelperService userHelperService) {
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
