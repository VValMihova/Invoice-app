package bg.softuni.invoice_app.validation.vallidation.annotation;

import bg.softuni.invoice_app.validation.vallidation.UniqueVatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueVatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueVat {
  String message() default "Company with this VAT already exist!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
