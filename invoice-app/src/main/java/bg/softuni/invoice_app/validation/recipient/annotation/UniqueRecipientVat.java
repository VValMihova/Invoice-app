package bg.softuni.invoice_app.validation.recipient.annotation;

import bg.softuni.invoice_app.validation.recipient.UniqueRecipientVatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueRecipientVatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueRecipientVat {
  String message() default "You already have a recipient with this VAT number!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
