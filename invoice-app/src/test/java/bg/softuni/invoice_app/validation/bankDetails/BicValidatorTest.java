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
public class BicValidatorTest {
  
  @Mock
  private ConstraintValidatorContext mockContext;
  
  private BicValidator toTest;
  
  @BeforeEach
  void setUp() {
    toTest = new BicValidator();
  }
  
  @Test
  void testWithNullBic() {
    boolean result = toTest.isValid(null, mockContext);
    assertTrue(result);
  }
  
  @Test
  void testWithEmptyBic() {
    boolean result = toTest.isValid("", mockContext);
    assertFalse(result);
  }
  
  @Test
  void testWithValidBic() {
    String validBic = "DEUTDEFF";
    boolean result = toTest.isValid(validBic, mockContext);
    assertTrue(result);
  }
  
  @Test
  void testWithInvalidBic() {
    String invalidBic = "INVALID123";
    boolean result = toTest.isValid(invalidBic, mockContext);
    assertFalse(result);
  }
  
  @Test
  void testWithValidBicAndBranchCode() {
    String validBicWithBranch = "DEUTDEFF500";
    boolean result = toTest.isValid(validBicWithBranch, mockContext);
    assertTrue(result);
  }
  
  @Test
  void testWithInvalidBicLength() {
    String invalidBicLength = "DEUTDEFF500X";
    boolean result = toTest.isValid(invalidBicLength, mockContext);
    assertFalse(result);
  }
}
