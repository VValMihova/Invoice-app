package bg.softuni.invoice_app.validation.report;

import bg.softuni.invoice_app.model.dto.ReportCriteria;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, ReportCriteria> {
  private String message;
  
  @Override
  public void initialize(ValidDateRange constraintAnnotation) {
    this.message = constraintAnnotation.message();
  }
  
  @Override
  public boolean isValid(ReportCriteria criteria, ConstraintValidatorContext context) {
    if (criteria.getStartDate() == null || criteria.getEndDate() == null) {
      return true;
    }
    LocalDate startDate = criteria.getStartDate();
    LocalDate endDate = criteria.getEndDate();
    boolean valid = startDate.isBefore(endDate) || startDate.isEqual(endDate);
    if (!valid){
      context.unwrap(HibernateConstraintValidatorContext.class)
          .buildConstraintViolationWithTemplate(message)
          .addPropertyNode("startDate")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
    }
    return valid;
  }
}
