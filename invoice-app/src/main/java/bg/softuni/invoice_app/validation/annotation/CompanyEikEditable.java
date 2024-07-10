package bg.softuni.invoice_app.validation.annotation;

import bg.softuni.invoice_app.validation.CompanyEikEditableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CompanyEikEditableValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompanyEikEditable {
  String message() default "Company with this EIK is already taken!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
