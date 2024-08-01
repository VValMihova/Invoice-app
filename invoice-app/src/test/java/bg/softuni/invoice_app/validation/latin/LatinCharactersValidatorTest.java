package bg.softuni.invoice_app.validation.latin;

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
public class LatinCharactersValidatorTest {
  
  private LatinCharactersValidator toTest;
  
  @Mock
  private ConstraintValidatorContext context;
  
  @BeforeEach
  void setUp() {
    toTest = new LatinCharactersValidator();
  }
  
  @Test
  void valueIsNull_shouldReturnTrue() {
    boolean result = toTest.isValid(null, context);
    assertTrue(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsOnlyLatinCharacters_shouldReturnTrue() {
    boolean result = toTest.isValid("TestValue", context);
    assertTrue(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsNonLatinCharacters_shouldReturnFalse() {
    boolean result = toTest.isValid("ТЕСТ СТОЙНОСТ", context);
    assertFalse(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsSpaces_shouldReturnFalse() {
    boolean result = toTest.isValid("Test Value", context);
    assertFalse(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsNumbers_shouldReturnFalse() {
    boolean result = toTest.isValid("Test123", context);
    assertFalse(result);
    verifyNoInteractions(context);
  }
  
  @Test
  void valueContainsSpecialCharacters_shouldReturnFalse() {
    boolean result = toTest.isValid("Test@Value", context);
    assertFalse(result);
    verifyNoInteractions(context);
  }
}
