package bg.softuni.invoice_app.validation.registration;

import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static bg.softuni.invoice_app.TestConstants.COMPANY_VAT_NUMBER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UniqueVatValidatorTest {
  
  @Mock
  private UserService userService;
  
  @Mock
  private ConstraintValidatorContext constraintValidatorContext;
  
  @InjectMocks
  private UniqueVatValidator uniqueVatValidator;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    uniqueVatValidator = new UniqueVatValidator(userService);
  }
  
  @Test
  void testIsValid_withNullVat() {
    assertTrue(uniqueVatValidator.isValid(null, constraintValidatorContext));
  }
  
  @Test
  void testIsValid_withUniqueVat() {
    String vat = COMPANY_VAT_NUMBER;
    when(userService.getUserByCompanyVat(vat)).thenReturn(null);
    
    assertTrue(uniqueVatValidator.isValid(vat, constraintValidatorContext));
  }
  
  @Test
  void testIsValid_withExistingVat() {
    String vat = COMPANY_VAT_NUMBER;
    when(userService.getUserByCompanyVat(vat)).thenReturn(new User());
    
    assertFalse(uniqueVatValidator.isValid(vat, constraintValidatorContext));
  }
}
