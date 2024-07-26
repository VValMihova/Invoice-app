package bg.softuni.invoice_app.validation.recipient.annotation;

import bg.softuni.invoice_app.validation.recipient.RecipientCompanyNameEditableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RecipientCompanyNameEditableValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RecipientCompanyNameEditable {
  String message() default "{custom.validations.recipient.exists.companyName}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}

