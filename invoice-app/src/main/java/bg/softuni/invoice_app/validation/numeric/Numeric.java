package bg.softuni.invoice_app.validation.numeric;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NumericValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Numeric {
  
  String message() default "Полето трябва да съдържа само цифри.";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}