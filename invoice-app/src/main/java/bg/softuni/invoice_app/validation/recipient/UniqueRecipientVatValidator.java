package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.validation.recipient.annotation.UniqueRecipientVat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueRecipientVatValidator implements ConstraintValidator<UniqueRecipientVat, String> {
  private final RecipientDetailsService recipientDetailsService;
  
  public UniqueRecipientVatValidator(RecipientDetailsService recipientDetailsService) {
    this.recipientDetailsService = recipientDetailsService;
  }
  
  @Override
  public boolean isValid(String vatNumber, ConstraintValidatorContext constraintValidatorContext) {
    return recipientDetailsService.findAll().stream()
        .noneMatch(recipient -> recipient.getVatNumber().equals(vatNumber));
  }
}