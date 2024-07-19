package bg.softuni.invoice_app.validation.password;

import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegisterBindingDto> {
  
  @Override
  public void initialize(PasswordMatches constraintAnnotation) {
  }
  
  @Override
  public boolean isValid(UserRegisterBindingDto userRegisterBindingDto, ConstraintValidatorContext context) {
    if (userRegisterBindingDto.getPassword() == null
        || userRegisterBindingDto.getConfirmPassword() == null) {
      return true;
    }
    
    boolean isValid = userRegisterBindingDto.getPassword().equals(userRegisterBindingDto.getConfirmPassword());
    
    if (!isValid) {
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("confirmPassword")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
    }
    
    return isValid;
  }
}