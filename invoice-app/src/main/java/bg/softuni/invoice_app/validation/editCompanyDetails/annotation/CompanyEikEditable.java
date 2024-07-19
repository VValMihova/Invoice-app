package bg.softuni.invoice_app.validation.editCompanyDetails.annotation;

import bg.softuni.invoice_app.validation.editCompanyDetails.CompanyEikEditableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CompanyEikEditableValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompanyEikEditable {
  String message() default "{custom.validations.unique.eik}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
