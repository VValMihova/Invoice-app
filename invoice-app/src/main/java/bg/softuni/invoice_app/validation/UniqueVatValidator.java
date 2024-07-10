package bg.softuni.invoice_app.validation;

import bg.softuni.invoice_app.service.UserService;
import bg.softuni.invoice_app.validation.annotation.UniqueVat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueVatValidator  implements ConstraintValidator<UniqueVat, String> {
  private final UserService userService;
  
  public UniqueVatValidator(UserService userService) {
    this.userService = userService;
  }
  
  @Override
  public boolean isValid(String vat, ConstraintValidatorContext constraintValidatorContext) {
    return this.userService.getUserByCompanyVat(vat) == null;
  }
}
