package bg.softuni.invoice_app.validation.registration;

import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static bg.softuni.invoice_app.TestConstants.COMPANY_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UniqueCompanyNameValidatorTest {
  
  @Mock
  private CompanyDetailsService companyDetailsService;
  
  @Mock
  private ConstraintValidatorContext constraintValidatorContext;
  
  @InjectMocks
  private UniqueCompanyNameValidator uniqueCompanyNameValidator;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    uniqueCompanyNameValidator = new UniqueCompanyNameValidator(companyDetailsService);
  }
  
  @Test
  void testIsValid_withNullCompanyName() {
    assertTrue(uniqueCompanyNameValidator.isValid(null, constraintValidatorContext));
  }
  
  @Test
  void testIsValid_withUniqueCompanyName() {
    String companyName = COMPANY_NAME;
    when(companyDetailsService.existsByCompanyName(companyName)).thenReturn(false);
    
    assertTrue(uniqueCompanyNameValidator.isValid(companyName, constraintValidatorContext));
  }
  
  @Test
  void testIsValid_withExistingCompanyName() {
    String companyName = COMPANY_NAME;
    when(companyDetailsService.existsByCompanyName(companyName)).thenReturn(true);
    
    assertFalse(uniqueCompanyNameValidator.isValid(companyName, constraintValidatorContext));
  }
}
