package bg.softuni.invoice_app.validation.registration;

import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.validation.registration.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
  
  private final UserService userService;
  
  public UniqueEmailValidator(UserService userService) {
    this.userService = userService;
  }
  
  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null) {
      return true;
    }
    return this.userService.getUserByEmail(email) == null;
  }
}