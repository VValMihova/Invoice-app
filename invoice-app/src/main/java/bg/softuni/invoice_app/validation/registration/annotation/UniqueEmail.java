package bg.softuni.invoice_app.validation.registration.annotation;

import bg.softuni.invoice_app.validation.registration.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
  String message() default "{custom.validations.email.exists}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
