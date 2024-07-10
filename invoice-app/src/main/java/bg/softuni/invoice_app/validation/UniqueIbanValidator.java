package bg.softuni.invoice_app.validation;

import bg.softuni.invoice_app.service.impl.UserHelperService;
import bg.softuni.invoice_app.validation.annotation.UniqueIban;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueIbanValidator implements ConstraintValidator<UniqueIban, String> {
  private final UserHelperService userHelperService;
  
  public UniqueIbanValidator(UserHelperService userHelperService) {
    this.userHelperService = userHelperService;
  }
  
  @Override
  public boolean isValid(String iban, ConstraintValidatorContext constraintValidatorContext) {
    return this.userHelperService
        .getBankAccounts()
        .stream()
        .noneMatch(bankAccount -> bankAccount.getIban().equals(iban));
  }
}
