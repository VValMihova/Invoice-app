package bg.softuni.invoice_app.validation.invoice;

import bg.softuni.invoice_app.service.invoice.InvoiceService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static bg.softuni.invoice_app.TestConstants.INVOICE_NUMBER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniqueInvoiceNumberValidatorTest {
  
  private UniqueInvoiceNumberValidator toTest;
  
  @Mock
  private InvoiceService invoiceService;
  
  @Mock
  private ConstraintValidatorContext context;
  
  @BeforeEach
  void setUp() {
    toTest = new UniqueInvoiceNumberValidator(invoiceService);
  }
  
  @Test
  void invoiceNumberIsNull_shouldReturnTrue() {
    boolean result = toTest.isValid(null, context);
    assertTrue(result);
    verifyNoInteractions(invoiceService);
  }
  
  @Test
  void invoiceNumberDoesNotExist_shouldReturnTrue() {
    Long invoiceNumber = INVOICE_NUMBER;
    when(invoiceService.checkInvoiceExists(invoiceNumber)).thenReturn(false);
    
    boolean result = toTest.isValid(invoiceNumber, context);
    
    assertTrue(result);
    verify(invoiceService).checkInvoiceExists(invoiceNumber);
  }
  
  @Test
  void invoiceNumberExists_shouldReturnFalse() {
    Long invoiceNumber = INVOICE_NUMBER;
    when(invoiceService.checkInvoiceExists(invoiceNumber)).thenReturn(true);
    
    boolean result = toTest.isValid(invoiceNumber, context);
    
    assertFalse(result);
    verify(invoiceService).checkInvoiceExists(invoiceNumber);
  }
}