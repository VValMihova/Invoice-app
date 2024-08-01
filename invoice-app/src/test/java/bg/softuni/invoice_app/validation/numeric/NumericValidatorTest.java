package bg.softuni.invoice_app.validation.numeric;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class NumericValidatorTest {
  
  private NumericValidator toTest;
  
  @Mock
  private ConstraintValidatorContext context;
  
  @BeforeEach
  void setUp() {
    toTest = new NumericValidator();
  }
  
  @Test
  void valueIsNull_shouldReturnTrue() {
    boolean result = toTest.isValid(null, context);
    assertTrue(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsOnlyDigits_shouldReturnTrue() {
    boolean result = toTest.isValid("123456", context);
    assertTrue(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsLetters_shouldReturnFalse() {
    boolean result = toTest.isValid("123a456", context);
    assertFalse(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsSpecialCharacters_shouldReturnFalse() {
    boolean result = toTest.isValid("123!456", context);
    assertFalse(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsSpaces_shouldReturnFalse() {
    boolean result = toTest.isValid("123 456", context);
    assertFalse(result);
    verifyNoInteractions(context);
  }
}
