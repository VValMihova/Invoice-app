package bg.softuni.invoiceappbankaccounts.validation;

import bg.softuni.invoiceappbankaccounts.validation.annotation.ValidIban;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class IbanValidator implements ConstraintValidator<ValidIban, String> {
  
  private static final Pattern IBAN_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");
  
  @Override
  public boolean isValid(String iban, ConstraintValidatorContext context) {
    if (iban == null) {
      return true;
    }
    iban = iban.replaceAll("\\s", "").toUpperCase();
    
    if (!IBAN_PATTERN.matcher(iban).matches() || iban.length() < 15 || iban.length() > 34) {
      return false;
    }
    return true;
  }
}
