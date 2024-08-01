package bg.softuni.invoice_app.validation.registration;

import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static bg.softuni.invoice_app.TestConstants.COMPANY_EIK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UniqueEikValidatorTest {
  
  @Mock
  private UserService userService;
  
  @Mock
  private ConstraintValidatorContext constraintValidatorContext;
  
  @InjectMocks
  private UniqueEikValidator uniqueEikValidator;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    uniqueEikValidator = new UniqueEikValidator(userService);
  }
  
  @Test
  void testIsValid_withNullEik() {
    assertTrue(uniqueEikValidator.isValid(null, constraintValidatorContext));
  }
  
  @Test
  void testIsValid_withUniqueEik() {
    String eik = COMPANY_EIK;
    when(userService.getUserByCompanyEik(eik)).thenReturn(null);
    
    assertTrue(uniqueEikValidator.isValid(eik, constraintValidatorContext));
  }
  
  @Test
  void testIsValid_withExistingEik() {
    String eik = COMPANY_EIK;
    when(userService.getUserByCompanyEik(eik)).thenReturn(new User());
    
    assertFalse(uniqueEikValidator.isValid(eik, constraintValidatorContext));
  }
}
