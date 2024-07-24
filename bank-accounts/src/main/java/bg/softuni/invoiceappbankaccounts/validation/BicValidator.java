package bg.softuni.invoiceappbankaccounts.validation;

import bg.softuni.invoiceappbankaccounts.validation.annotation.ValidBic;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class BicValidator implements ConstraintValidator<ValidBic, String> {
  
  private static final Pattern BIC_PATTERN = Pattern.compile("^[A-Za-z]{4}[A-Za-z]{2}[A-Za-z0-9]{2}([A-Za-z0-9]{3})?$");
  
  @Override
  public boolean isValid(String bic, ConstraintValidatorContext context) {
    if (bic == null) {
      return true;
    }
    
    return BIC_PATTERN.matcher(bic).matches();
  }
}