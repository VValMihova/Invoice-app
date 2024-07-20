package bg.softuni.invoice_app.validation.bankDetails;

import bg.softuni.invoice_app.validation.bankDetails.annotation.ValidIban;
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
