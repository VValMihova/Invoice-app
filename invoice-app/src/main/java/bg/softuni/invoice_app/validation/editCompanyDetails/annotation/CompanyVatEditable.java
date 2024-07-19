package bg.softuni.invoice_app.validation.editCompanyDetails.annotation;

import bg.softuni.invoice_app.validation.editCompanyDetails.CompanyVatEditableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CompanyVatEditableValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompanyVatEditable {
  String message() default "{custom.validations.unique.vat}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
