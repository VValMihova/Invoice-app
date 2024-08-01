package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static bg.softuni.invoice_app.TestConstants.COMPANY_EIK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UniqueRecipientEikValidatorTest {
  
  private UniqueRecipientEikValidator toTest;
  
  @Mock
  private RecipientDetailsService recipientDetailsService;
  
  @Mock
  private ConstraintValidatorContext context;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    toTest = new UniqueRecipientEikValidator(recipientDetailsService);
  }
  
  @Test
  void shouldReturnTrueWhenEikIsNull() {
    boolean result = toTest.isValid(null, context);
    
    assertTrue(result);
    verify(recipientDetailsService, never()).findAll();
  }
  
  @Test
  void shouldReturnFalseWhenEikExists() {
    String existingEik = COMPANY_EIK;
    RecipientDetailsView recipient = new RecipientDetailsView();
    recipient.setEik(existingEik);
    
    when(recipientDetailsService.findAll()).thenReturn(List.of(recipient));
    
    boolean result = toTest.isValid(existingEik, context);
    
    assertFalse(result);
    verify(recipientDetailsService).findAll();
  }
  
  @Test
  void shouldReturnTrueWhenEikDoesNotExist() {
    RecipientDetailsView recipient = new RecipientDetailsView();
    recipient.setEik("1234567890");
    
    when(recipientDetailsService.findAll()).thenReturn(List.of(recipient));
    
    boolean result = toTest.isValid(COMPANY_EIK, context);
    
    assertTrue(result);
    verify(recipientDetailsService).findAll();
  }
}