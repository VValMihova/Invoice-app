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
  String message() default "{custom.validations.recipient.exists.eik.same}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
