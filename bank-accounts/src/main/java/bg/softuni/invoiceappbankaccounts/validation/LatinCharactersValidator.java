package bg.softuni.invoiceappbankaccounts.validation;

import bg.softuni.invoiceappbankaccounts.validation.annotation.LatinCharacters;
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