package bg.softuni.invoice_app.validation.vatMatchesEik;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.lang.reflect.Field;

public class VatEikValidator implements ConstraintValidator<ValidVatEik, Object> {
  private String message;
  private String vatNumber;
  private String eik;
  
  @Override
  public void initialize(ValidVatEik constraintAnnotation) {
    this.vatNumber = constraintAnnotation.vatNumber();
    this.eik = constraintAnnotation.eik();
    this.message = constraintAnnotation.message();
  }
  
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {
      Field vatField = value.getClass().getDeclaredField(this.vatNumber);
      Field eikField = value.getClass().getDeclaredField(this.eik);
      
      vatField.setAccessible(true);
      eikField.setAccessible(true);
      
      String vatNumber = (String) vatField.get(value);
      String eik = (String) eikField.get(value);
      
      if (vatNumber == null || eik == null) {
        return true; // Валидацията за null трябва да се обработва чрез @NotNull
      }
      
      if (vatNumber.length() != eik.length() + 2) {
        return false;
      }
      
      String countryCode = vatNumber.substring(0, 2);
      String eikPart = vatNumber.substring(2);
      
      boolean match = countryCode.matches("[A-Z]{2}") && eikPart.equals(eik);
      
      if (!match) {
        context.unwrap(HibernateConstraintValidatorContext.class)
            .buildConstraintViolationWithTemplate(message)
            .addPropertyNode("vatNumber")
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
      }
      
      return match;
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      throw new RuntimeException("Error accessing fields", ex);
    }
  }
}