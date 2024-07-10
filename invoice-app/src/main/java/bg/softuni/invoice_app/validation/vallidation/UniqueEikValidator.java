package bg.softuni.invoice_app.validation.vallidation;

import bg.softuni.invoice_app.service.UserService;
import bg.softuni.invoice_app.validation.vallidation.annotation.UniqueEik;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEikValidator implements ConstraintValidator<UniqueEik, String> {
  private final UserService userService;
  
  public UniqueEikValidator(UserService userService) {
    this.userService = userService;
  }
  
  @Override
  public boolean isValid(String eik, ConstraintValidatorContext constraintValidatorContext) {
    return this.userService.getUserByCompanyEik(eik) == null;
  }
}
