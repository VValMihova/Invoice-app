package bg.softuni.invoice_app.validation.bankDetails;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class IbanValidatorTest {
  
  @Mock
  private ConstraintValidatorContext mockContext;
  
  private IbanValidator toTest;
  
  @BeforeEach
  void setUp() {
    toTest = new IbanValidator();
  }
  
  @Test
  void nullIbanShouldReturnTrue() {
    boolean result = toTest.isValid(null, mockContext);
    assertTrue(result);
  }
  
  @Test
  void emptyIbanShouldReturnFalse() {
    boolean result = toTest.isValid("", mockContext);
    assertFalse(result);
  }
  
  @Test
  void validIbanWithoutSpacesShouldReturnTrue() {
    String validIban = "DE89370400440532013000";
    boolean result = toTest.isValid(validIban, mockContext);
    assertTrue(result);
  }
  
  @Test
  void validIbanWithSpacesShouldReturnTrue() {
    String validIbanWithSpaces = "DE89 3704 0044 0532 0130 00";
    boolean result = toTest.isValid(validIbanWithSpaces, mockContext);
    assertTrue(result);
  }
  
  @Test
  void ibanWithInvalidCharactersShouldReturnFalse() {
    String invalidIban = "DE89@704#044%532*0130^00";
    boolean result = toTest.isValid(invalidIban, mockContext);
    assertFalse(result);
  }
  
  @Test
  void ibanWithLengthLessThan15ShouldReturnFalse() {
    String shortIban = "DE89370400440";
    boolean result = toTest.isValid(shortIban, mockContext);
    assertFalse(result);
  }
  
  @Test
  void ibanWithLengthMoreThan34ShouldReturnFalse() {
    String longIban = "DE893704004405320130000000000000000";
    boolean result = toTest.isValid(longIban, mockContext);
    assertFalse(result);
  }
  
  @Test
  void validIbanWithMixedCaseShouldReturnTrue() {
    String mixedCaseIban = "de89370400440532013000";
    boolean result = toTest.isValid(mixedCaseIban, mockContext);
    assertTrue(result);
  }
}
