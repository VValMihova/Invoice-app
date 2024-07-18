package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.validation.recipient.annotation.RecipientCompanyNameEditable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class RecipientCompanyNameEditableValidator implements ConstraintValidator<RecipientCompanyNameEditable, RecipientDetailsEdit> {
  private String message;
  private final RecipientDetailsService recipientDetailsService;
  
  public RecipientCompanyNameEditableValidator(RecipientDetailsService recipientDetailsService) {
    this.recipientDetailsService = recipientDetailsService;
  }
  
  @Override
  public void initialize(RecipientCompanyNameEditable constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    this.message = constraintAnnotation.message();
  }
  
  @Override
  public boolean isValid(RecipientDetailsEdit recipientDetailsEdit, ConstraintValidatorContext context) {
    if (recipientDetailsEdit.getId() == null) {
      return true;
    }
    
    String companyName = recipientDetailsEdit.getCompanyName();
    Long id = recipientDetailsEdit.getId();
    
    boolean companyExists = recipientDetailsService.existsByCompanyName(companyName);
    boolean sameCompany = recipientDetailsService.getById(id).getCompanyName().equals(companyName);
    
    if (companyExists && !sameCompany) {
      context.unwrap(HibernateConstraintValidatorContext.class)
          .buildConstraintViolationWithTemplate(message)
          .addPropertyNode("companyName")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
      return false;
    }
    
    return true;
  }
}


