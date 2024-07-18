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
    
    String rearrangedIban = iban.substring(4) + iban.substring(0, 4);
    
    StringBuilder numericIban = new StringBuilder();
    for (char character : rearrangedIban.toCharArray()) {
      int numericValue = Character.getNumericValue(character);
      if (numericValue < 0 || numericValue > 35) {
        return false;
      }
      numericIban.append(numericValue);
    }
    return new java.math.BigInteger(numericIban.toString()).mod(java.math.BigInteger.valueOf(97)).intValue() == 1;
  }
}
