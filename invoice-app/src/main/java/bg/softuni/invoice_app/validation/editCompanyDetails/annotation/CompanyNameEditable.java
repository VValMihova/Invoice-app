package bg.softuni.invoice_app.validation.editCompanyDetails.annotation;

import bg.softuni.invoice_app.validation.editCompanyDetails.CompanyNameEditableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CompanyNameEditableValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompanyNameEditable {
  String message() default "{custom.validations.unique.companyName}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
