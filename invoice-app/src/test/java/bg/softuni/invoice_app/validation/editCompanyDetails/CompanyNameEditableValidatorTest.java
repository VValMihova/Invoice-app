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

import static bg.softuni.invoice_app.TestConstants.COMPANY_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyNameEditableValidatorTest {
  
  @Mock
  private CompanyDetailsService companyDetailsService;
  
  @Mock
  private UserService userService;
  
  @Mock
  private ConstraintValidatorContext context;
  
  private CompanyNameEditableValidator toTest;
  
  @BeforeEach
  void setUp() {
    toTest = new CompanyNameEditableValidator(companyDetailsService, userService);
  }
  
  @Test
  void companyNameIsNull_shouldReturnTrue() {
    boolean result = toTest.isValid(null, context);
    assertTrue(result);
  }
  
  @Test
  void companyNameDoesNotExist_shouldReturnTrue() {
    when(companyDetailsService.existsByCompanyName(COMPANY_NAME)).thenReturn(false);
    
    boolean result = toTest.isValid(COMPANY_NAME, context);
    
    assertTrue(result);
    verify(companyDetailsService).existsByCompanyName(COMPANY_NAME);
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
  
  @Test
  void companyNameExistsAndBelongsToCurrentUser_shouldReturnTrue() {
    when(companyDetailsService.existsByCompanyName(COMPANY_NAME)).thenReturn(true);
    CompanyDetailsView companyDetails = new CompanyDetailsView();
    companyDetails.setCompanyName(COMPANY_NAME);
    when(userService.showCompanyDetails()).thenReturn(companyDetails);
    
    boolean result = toTest.isValid(COMPANY_NAME, context);
    
    assertTrue(result);
    verify(companyDetailsService).existsByCompanyName(COMPANY_NAME);
    verify(userService).showCompanyDetails();
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
  
  @Test
  void companyNameExistsAndDoesNotBelongToCurrentUser_shouldReturnFalse() {
    when(companyDetailsService.existsByCompanyName(COMPANY_NAME)).thenReturn(true);
    CompanyDetailsView companyDetails = new CompanyDetailsView();
    companyDetails.setCompanyName("DIFFERENT_COMPANY_NAME");
    when(userService.showCompanyDetails()).thenReturn(companyDetails);
    
    boolean result = toTest.isValid(COMPANY_NAME, context);
    
    assertFalse(result);
    verify(companyDetailsService).existsByCompanyName(COMPANY_NAME);
    verify(userService).showCompanyDetails();
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
}
