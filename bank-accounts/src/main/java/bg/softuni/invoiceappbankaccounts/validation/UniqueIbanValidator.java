package bg.softuni.invoiceappbankaccounts.validation;

import bg.softuni.invoiceappbankaccounts.repository.BankAccountRepository;
import bg.softuni.invoiceappbankaccounts.validation.annotation.UniqueIban;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueIbanValidator implements ConstraintValidator<UniqueIban, String> {
  private final BankAccountRepository bankAccountRepository;
  
  public UniqueIbanValidator(BankAccountRepository bankAccountRepository) {
    this.bankAccountRepository = bankAccountRepository;
  }
  
  @Override
  public boolean isValid(String iban, ConstraintValidatorContext constraintValidatorContext) {
    if (iban == null) {
      return true;
    }
    
    return !this.bankAccountRepository
        .findAll()
        .stream()
        .anyMatch(bankAccount -> bankAccount.getIban().equals(iban));
  }
}