package bg.softuni.invoice_app.validation.recipient.annotation;

import bg.softuni.invoice_app.validation.recipient.UniqueRecipientCompanyNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueRecipientCompanyNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueRecipientCompanyName {
  String message() default "You already have a recipient with this company name!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
