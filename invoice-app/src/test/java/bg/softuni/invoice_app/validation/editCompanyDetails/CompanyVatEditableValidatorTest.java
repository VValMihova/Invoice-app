package bg.softuni.invoice_app.validation.editCompanyDetails;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static bg.softuni.invoice_app.TestConstants.COMPANY_VAT_NUMBER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyVatEditableValidatorTest {
  
  @Mock
  private CompanyDetailsService companyDetailsService;
  
  @Mock
  private UserService userService;
  
  @Mock
  private ConstraintValidatorContext context;
  
  private CompanyVatEditableValidator toTest;
  
  @BeforeEach
  void setUp() {
    toTest = new CompanyVatEditableValidator(companyDetailsService, userService);
  }
  
  @Test
  void vatIsNull_shouldReturnTrue() {
    boolean result = toTest.isValid(null, context);
    assertTrue(result);
  }
  
  @Test
  void vatDoesNotExist_shouldReturnTrue() {
    when(companyDetailsService.existsByVatNumber(COMPANY_VAT_NUMBER)).thenReturn(false);
    
    boolean result = toTest.isValid(COMPANY_VAT_NUMBER, context);
    
    assertTrue(result);
    verify(companyDetailsService).existsByVatNumber(COMPANY_VAT_NUMBER);
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
  
  @Test
  void vatExistsAndBelongsToCurrentUser_shouldReturnTrue() {
    when(companyDetailsService.existsByVatNumber(COMPANY_VAT_NUMBER)).thenReturn(true);
    CompanyDetailsView companyDetails = new CompanyDetailsView();
    companyDetails.setVatNumber(COMPANY_VAT_NUMBER);
    when(userService.showCompanyDetails()).thenReturn(companyDetails);
    
    boolean result = toTest.isValid(COMPANY_VAT_NUMBER, context);
    
    assertTrue(result);
    verify(companyDetailsService).existsByVatNumber(COMPANY_VAT_NUMBER);
    verify(userService).showCompanyDetails();
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
  
  @Test
  void vatExistsAndDoesNotBelongToCurrentUser_shouldReturnFalse() {
    when(companyDetailsService.existsByVatNumber(COMPANY_VAT_NUMBER)).thenReturn(true);
    CompanyDetailsView companyDetails = new CompanyDetailsView();
    companyDetails.setVatNumber("DIFFERENT_VAT_NUMBER");
    when(userService.showCompanyDetails()).thenReturn(companyDetails);
    
    boolean result = toTest.isValid(COMPANY_VAT_NUMBER, context);
    
    assertFalse(result);
    verify(companyDetailsService).existsByVatNumber(COMPANY_VAT_NUMBER);
    verify(userService).showCompanyDetails();
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
}
