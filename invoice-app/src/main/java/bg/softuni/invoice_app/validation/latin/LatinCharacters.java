package bg.softuni.invoice_app.validation.latin;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LatinCharactersValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface LatinCharacters {
  
  String message() default "Полето трябва да съдържа само латински букви.";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}