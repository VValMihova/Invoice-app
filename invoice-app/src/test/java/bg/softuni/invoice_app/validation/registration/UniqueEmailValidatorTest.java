package bg.softuni.invoice_app.validation.registration;

import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UniqueEmailValidatorTest {
  
  @Mock
  private UserService userService;
  
  @Mock
  private ConstraintValidatorContext constraintValidatorContext;
  
  @InjectMocks
  private UniqueEmailValidator uniqueEmailValidator;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    uniqueEmailValidator = new UniqueEmailValidator(userService);
  }
  
  @Test
  void testIsValid_withNullEmail() {
    assertTrue(uniqueEmailValidator.isValid(null, constraintValidatorContext));
  }
  
  @Test
  void testIsValid_withUniqueEmail() {
    String email = "unique@example.com";
    when(userService.getUserByEmail(email)).thenReturn(null);
    
    assertTrue(uniqueEmailValidator.isValid(email, constraintValidatorContext));
  }
  
  @Test
  void testIsValid_withExistingEmail() {
    String email = "existing@example.com";
    when(userService.getUserByEmail(email)).thenReturn(new User());
    
    assertFalse(uniqueEmailValidator.isValid(email, constraintValidatorContext));
  }
}


