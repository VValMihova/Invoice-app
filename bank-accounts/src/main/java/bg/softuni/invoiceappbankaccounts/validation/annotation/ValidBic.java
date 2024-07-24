package bg.softuni.invoiceappbankaccounts.validation.annotation;

import bg.softuni.invoiceappbankaccounts.validation.BicValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BicValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBic {
  
  String message() default "{custom.validations.invalid.bic}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}