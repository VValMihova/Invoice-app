package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsProvider;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.validation.recipient.annotation.NotSameUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class NotSameUserDetailsValidator implements ConstraintValidator<NotSameUser, RecipientDetailsProvider> {
  private String message;
  private final UserService userService;
  
  public NotSameUserDetailsValidator(UserService userService) {
    this.userService = userService;
  }
  
  @Override
  public void initialize(NotSameUser constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    this.message = constraintAnnotation.message();
  }
  
  @Override
  public boolean isValid( RecipientDetailsProvider recipientDetailsProvider,
                         ConstraintValidatorContext constraintValidatorContext) {
    CompanyDetailsView userCompanyDetails = this.userService.showCompanyDetails();
    
    boolean match = !userCompanyDetails.getCompanyName().equals(recipientDetailsProvider.getCompanyName())
                && !userCompanyDetails.getEik().equals(recipientDetailsProvider.getEik())
                && !userCompanyDetails.getVatNumber().equals(recipientDetailsProvider.getVatNumber());
    
    if (!match) {
      constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)
          .buildConstraintViolationWithTemplate(message)
          .addPropertyNode("companyName")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
    }
    return match;
  }
}
