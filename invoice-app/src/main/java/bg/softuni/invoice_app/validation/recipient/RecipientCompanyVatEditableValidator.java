package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.validation.recipient.annotation.RecipientCompanyVatEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RecipientCompanyVatEditableValidator implements ConstraintValidator<RecipientCompanyVatEditable, RecipientDetailsEdit> {
  
  private final RecipientDetailsService recipientDetailsService;
  
  public RecipientCompanyVatEditableValidator(RecipientDetailsService recipientDetailsService) {
    this.recipientDetailsService = recipientDetailsService;
  }
  
  @Override
  public boolean isValid(RecipientDetailsEdit recipientDetailsEdit, ConstraintValidatorContext context) {
    if (recipientDetailsEdit.getId() == null) {
      return true;
    }
    
    String vat = recipientDetailsEdit.getVatNumber();
    Long id = recipientDetailsEdit.getId();
    
    boolean companyExists = recipientDetailsService.existsByVatNumber(vat);
    boolean sameCompany = recipientDetailsService.getById(id).getVatNumber().equals(vat);
    
    if (companyExists && !sameCompany) {
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("vatNumber")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
      return false;
    }
    
    return true;
  }
}

