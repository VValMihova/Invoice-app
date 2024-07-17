package bg.softuni.invoice_app.validation.latin;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LatinCharactersValidator implements ConstraintValidator<LatinCharacters, String> {
  
  private static final String LATIN_PATTERN = "^[a-zA-Z]+$";
  
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return value.matches(LATIN_PATTERN);
  }
}