package bg.softuni.invoice_app.validation.vallidation;

import bg.softuni.invoice_app.service.UserService;
import bg.softuni.invoice_app.validation.vallidation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return this.userService.getUserByEmail(email) == null;
    }
}