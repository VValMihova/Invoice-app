package bg.softuni.invoice_app.validation.registration.annotation;

import bg.softuni.invoice_app.validation.registration.UniqueVatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueVatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueVat {
  String message() default "{custom.validations.company.vat.exists}";
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
