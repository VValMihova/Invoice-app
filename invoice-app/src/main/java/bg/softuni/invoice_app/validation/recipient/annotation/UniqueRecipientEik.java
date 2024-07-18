package bg.softuni.invoice_app.validation.recipient.annotation;

import bg.softuni.invoice_app.validation.recipient.UniqueRecipientEikValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueRecipientEikValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueRecipientEik {
  String message() default "You already have a recipient with this EIK!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
