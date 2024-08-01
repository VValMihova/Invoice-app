package bg.softuni.invoice_app.validation.password;

import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static bg.softuni.invoice_app.TestConstants.TEST_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PasswordMatchesValidatorTest {
  
  private PasswordMatchesValidator toTest;
  
  @Mock
  private ConstraintValidatorContext context;
  
  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    toTest = new PasswordMatchesValidator();
  }
  
  @Test
  void passwordsMatch_shouldReturnTrue() {
    UserRegisterBindingDto dto = new UserRegisterBindingDto();
    dto.setPassword(TEST_PASSWORD);
    dto.setConfirmPassword(TEST_PASSWORD);
    
    boolean result = toTest.isValid(dto, context);
    
    assertTrue(result);
    verify(context, never()).buildConstraintViolationWithTemplate(anyString());
  }
  
  
  @Test
  void passwordIsNull_shouldReturnTrue() {
    UserRegisterBindingDto dto = new UserRegisterBindingDto();
    dto.setPassword(null);
    dto.setConfirmPassword(TEST_PASSWORD);
    
    boolean result = toTest.isValid(dto, context);
    
    assertTrue(result);
    verify(context, never()).buildConstraintViolationWithTemplate(anyString());
  }
  
  @Test
  void confirmPasswordIsNull_shouldReturnTrue() {
    UserRegisterBindingDto dto = new UserRegisterBindingDto();
    dto.setPassword(TEST_PASSWORD);
    dto.setConfirmPassword(null);
    
    boolean result = toTest.isValid(dto, context);
    
    assertTrue(result);
    verify(context, never()).buildConstraintViolationWithTemplate(anyString());
  }
}