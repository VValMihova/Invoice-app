package bg.softuni.invoice_app.validation.invoice;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SameUserDetailsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SameUser {
  String message() default "You can't be recipient!";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}

