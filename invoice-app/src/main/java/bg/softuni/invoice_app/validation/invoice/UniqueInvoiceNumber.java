package bg.softuni.invoice_app.validation.invoice;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueInvoiceNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueInvoiceNumber {
  String message() default "Invoice with this number already exist!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
