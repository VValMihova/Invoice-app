package bg.softuni.invoice_app.validation.bankAccount;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueIbanValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIban {
  String message() default "{UniqueIban.message}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}

