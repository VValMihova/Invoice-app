package bg.softuni.invoice_app.validation.recipient.annotation;

import bg.softuni.invoice_app.validation.recipient.RecipientCompanyVatEditableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RecipientCompanyVatEditableValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RecipientCompanyVatEditable {
  String message() default "{custom.validations.recipient.exists.vat}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}

