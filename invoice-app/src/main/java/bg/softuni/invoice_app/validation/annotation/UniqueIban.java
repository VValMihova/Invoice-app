package bg.softuni.invoice_app.validation.annotation;

import bg.softuni.invoice_app.validation.UniqueIbanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueIbanValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIban {
  String message() default "Bank account with this IBAN already exist!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
