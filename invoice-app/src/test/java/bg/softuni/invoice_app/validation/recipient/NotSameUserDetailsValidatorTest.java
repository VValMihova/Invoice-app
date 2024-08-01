package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsProvider;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.validation.recipient.annotation.NotSameUser;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class NotSameUserDetailsValidatorTest {
  
  private NotSameUserDetailsValidator toTest;
  
  @Mock
  private UserService userService;
  
  @Mock
  private ConstraintValidatorContext context;
  
  @Mock
  private HibernateConstraintValidatorContext hibernateContext;
  
  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    toTest = new NotSameUserDetailsValidator(userService);
    toTest.initialize(mock(NotSameUser.class));
  }
  
  @Test
  void shouldReturnTrueWhenDetailsDoNotMatch() {
    CompanyDetailsView userDetails = new CompanyDetailsView();
    userDetails.setCompanyName(COMPANY_NAME);
    userDetails.setEik(COMPANY_EIK);
    userDetails.setVatNumber(COMPANY_VAT_NUMBER);
    
    RecipientDetailsProvider recipientDetails = mock(RecipientDetailsProvider.class);
    when(recipientDetails.getCompanyName()).thenReturn(ANOTHER_COMPANY_NAME);
    when(recipientDetails.getEik()).thenReturn(ANOTHER_COMPANY_EIK);
    when(recipientDetails.getVatNumber()).thenReturn(ANOTHER_COMPANY_VAT);
    
    when(userService.showCompanyDetails()).thenReturn(userDetails);
    
    boolean result = toTest.isValid(recipientDetails, context);
    
    assertTrue(result);
    verify(context, never()).buildConstraintViolationWithTemplate(anyString());
  }
}