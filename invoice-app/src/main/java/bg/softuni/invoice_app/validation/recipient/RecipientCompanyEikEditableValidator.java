package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.validation.recipient.annotation.RecipientCompanyEikEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RecipientCompanyEikEditableValidator implements ConstraintValidator<RecipientCompanyEikEditable, RecipientDetailsEdit> {
  
  private final RecipientDetailsService recipientDetailsService;
  
  public RecipientCompanyEikEditableValidator(RecipientDetailsService recipientDetailsService) {
    this.recipientDetailsService = recipientDetailsService;
  }
  
  @Override
  public boolean isValid(RecipientDetailsEdit recipientDetailsEdit, ConstraintValidatorContext context) {
    if (recipientDetailsEdit.getId() == null) {
      return true;
    }
    
    String eik = recipientDetailsEdit.getEik();
    Long id = recipientDetailsEdit.getId();
    
    boolean companyExists = recipientDetailsService.existsByEik(eik);
    boolean sameCompany = recipientDetailsService.getById(id).getEik().equals(eik);
    
    if (companyExists && !sameCompany) {
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("eik")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
      return false;
    }
    
    return true;
  }
}