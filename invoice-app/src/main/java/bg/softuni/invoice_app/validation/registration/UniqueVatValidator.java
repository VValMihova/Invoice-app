package bg.softuni.invoice_app.validation.registration;

import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.validation.registration.annotation.UniqueVat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueVatValidator implements ConstraintValidator<UniqueVat, String> {
  private final UserService userService;
  
  public UniqueVatValidator(UserService userService) {
    this.userService = userService;
  }
  
  @Override
  public boolean isValid(String vat, ConstraintValidatorContext constraintValidatorContext) {
    if (vat == null) {
      return true;
    }
    return this.userService.getUserByCompanyVat(vat) == null;
  }
}
