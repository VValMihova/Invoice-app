package bg.softuni.invoice_app.validation.recipient;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static bg.softuni.invoice_app.TestConstants.COMPANY_VAT_NUMBER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UniqueRecipientVatValidatorTest {
  
  private UniqueRecipientVatValidator toTest;
  
  @Mock
  private RecipientDetailsService recipientDetailsService;
  
  @Mock
  private ConstraintValidatorContext context;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    toTest = new UniqueRecipientVatValidator(recipientDetailsService);
  }
  
  @Test
  void shouldReturnTrueWhenVatNumberIsNull() {
    boolean result = toTest.isValid(null, context);
    
    assertTrue(result);
    verify(recipientDetailsService, never()).findAll();
  }
  
  @Test
  void shouldReturnFalseWhenVatNumberExists() {
    String existingVat = COMPANY_VAT_NUMBER;
    RecipientDetailsView recipient = new RecipientDetailsView();
    recipient.setVatNumber(existingVat);
    
    when(recipientDetailsService.findAll()).thenReturn(List.of(recipient));
    
    boolean result = toTest.isValid(existingVat, context);
    
    assertFalse(result);
    verify(recipientDetailsService).findAll();
  }
  
  @Test
  void shouldReturnTrueWhenVatNumberDoesNotExist() {
    RecipientDetailsView recipient = new RecipientDetailsView();
    recipient.setVatNumber("BG1234567890");
    
    when(recipientDetailsService.findAll()).thenReturn(List.of(recipient));
    
    boolean result = toTest.isValid(COMPANY_VAT_NUMBER, context);
    
    assertTrue(result);
    verify(recipientDetailsService).findAll();
  }
}