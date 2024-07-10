package bg.softuni.invoice_app.validation.vallidation.annotation;

import bg.softuni.invoice_app.validation.vallidation.UniqueEikValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEikValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEik {
  String message() default "Company with this EIK already exist!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
