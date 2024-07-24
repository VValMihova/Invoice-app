package bg.softuni.invoiceappbankaccounts.validation.annotation;

import bg.softuni.invoiceappbankaccounts.validation.UniqueIbanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueIbanValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIban {
  String message() default "{custom.validations.unique.iban}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}

