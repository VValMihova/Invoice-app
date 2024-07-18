package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.validation.recipient.annotation.UniqueRecipientEik;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueRecipientEikValidator implements ConstraintValidator<UniqueRecipientEik, String> {
  private final RecipientDetailsService recipientDetailsService;
  
  public UniqueRecipientEikValidator(RecipientDetailsService recipientDetailsService) {
    this.recipientDetailsService = recipientDetailsService;
  }
  
  @Override
  public boolean isValid(String eik, ConstraintValidatorContext constraintValidatorContext) {
    if (eik == null){
      return true;
    }
    return recipientDetailsService.findAll().stream()
        .noneMatch(recipient -> recipient.getEik().equals(eik));
  }
}