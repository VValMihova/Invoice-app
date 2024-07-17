package bg.softuni.invoice_app.validation.invoice;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.service.user.UserHelperService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SameUserDetailsValidator implements ConstraintValidator<SameUser, RecipientDetailsAddDto> {
  
  private final UserService userService;
  
  public SameUserDetailsValidator(UserService userService) {
    this.userService = userService;
  }
  
  @Override
  public boolean isValid(RecipientDetailsAddDto recipientDetailsAddDto, ConstraintValidatorContext constraintValidatorContext) {
    CompanyDetailsView userCompanyDetails = this.userService.showCompanyDetails();
    return
        !userCompanyDetails.getCompanyName().equals(recipientDetailsAddDto.getCompanyName())
        && !userCompanyDetails.getEik().equals(recipientDetailsAddDto.getEik())
        && !userCompanyDetails.getVatNumber().equals(recipientDetailsAddDto.getVatNumber());
  }
}
