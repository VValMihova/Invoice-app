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

import static bg.softuni.invoice_app.TestConstants.COMPANY_EIK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyEikEditableValidatorTest {
  
  @Mock
  private CompanyDetailsService companyDetailsService;
  
  @Mock
  private UserService userService;
  
  @Mock
  private ConstraintValidatorContext context;
  
  private CompanyEikEditableValidator toTest;
  
  @BeforeEach
  void setUp() {
    toTest = new CompanyEikEditableValidator(companyDetailsService, userService);
  }
  
  @Test
  void whenEikIsNull_shouldReturnTrue() {
    boolean result = toTest.isValid(null, context);
    assertTrue(result);
  }
  
  @Test
  void eikDoesNotExist_shouldReturnTrue() {
    when(companyDetailsService.existsByEik(COMPANY_EIK)).thenReturn(false);
    
    boolean result = toTest.isValid(COMPANY_EIK, context);
    
    assertTrue(result);
    verify(companyDetailsService).existsByEik(COMPANY_EIK);
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
  
  @Test
  void eikExistsAndBelongsToCurrentUser_shouldReturnTrue() {
    when(companyDetailsService.existsByEik(COMPANY_EIK)).thenReturn(true);
    CompanyDetailsView companyDetails = new CompanyDetailsView();
    companyDetails.setEik(COMPANY_EIK);
    when(userService.showCompanyDetails()).thenReturn(companyDetails);
    
    boolean result = toTest.isValid(COMPANY_EIK, context);
    
    assertTrue(result);
    verify(companyDetailsService).existsByEik(COMPANY_EIK);
    verify(userService).showCompanyDetails();
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
  
  @Test
  void eikExistsAndDoesNotBelongToCurrentUser_shouldReturnFalse() {
    when(companyDetailsService.existsByEik(COMPANY_EIK)).thenReturn(true);
    CompanyDetailsView companyDetails = new CompanyDetailsView();
    companyDetails.setEik("DIFFERENT_EIK");
    when(userService.showCompanyDetails()).thenReturn(companyDetails);
    
    boolean result = toTest.isValid(COMPANY_EIK, context);
    
    assertFalse(result);
    verify(companyDetailsService).existsByEik(COMPANY_EIK);
    verify(userService).showCompanyDetails();
    verifyNoMoreInteractions(companyDetailsService, userService);
  }
}
