package bg.softuni.invoice_app.validation.recipient.annotation;

import bg.softuni.invoice_app.validation.recipient.RecipientCompanyEikEditableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RecipientCompanyEikEditableValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RecipientCompanyEikEditable {
  String message() default "You already have a recipient with such EIK!";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}