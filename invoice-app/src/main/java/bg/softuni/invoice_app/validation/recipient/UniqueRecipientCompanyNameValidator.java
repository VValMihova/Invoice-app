package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.validation.recipient.annotation.UniqueRecipientCompanyName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueRecipientCompanyNameValidator implements ConstraintValidator<UniqueRecipientCompanyName, String> {
  private final RecipientDetailsService recipientDetailsService;
  
  public UniqueRecipientCompanyNameValidator(RecipientDetailsService recipientDetailsService) {
    this.recipientDetailsService = recipientDetailsService;
  }
  
  @Override
  public boolean isValid(String companyName, ConstraintValidatorContext constraintValidatorContext) {
    if (companyName == null) {
      return true;
    }
    
    return recipientDetailsService.findAll().stream()
        .noneMatch(recipient -> recipient.getCompanyName().equals(companyName));
  }
}
