package bg.softuni.invoice_app.service.arcive;

import bg.softuni.invoice_app.model.entity.ArchiveSale;
import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import bg.softuni.invoice_app.service.archive.ArchiveSaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static bg.softuni.invoice_app.TestConstants.INVOICE_NUMBER;
import static bg.softuni.invoice_app.TestConstants.TEST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArchiveSaleServiceTest {
  
  @Mock
  private ArchiveSaleRepository mockArchiveSaleRepository;
  
  @InjectMocks
  private ArchiveSaleService toTest;
  
  @Test
  void testDeleteArchiveSales() {
    Long invoiceNumber = INVOICE_NUMBER;
    Long userId = TEST_ID;
    
    toTest.deleteArchiveSales(invoiceNumber, userId);
    
    verify(mockArchiveSaleRepository).deleteAllByInvoiceNumberAndUserId(invoiceNumber, userId);
  }
  
  @Test
  void testFindAllByInvoiceNumberAndUserId() {
    Long invoiceNumber = INVOICE_NUMBER;
    Long userId = TEST_ID;
    List<ArchiveSale> expectedSales = Collections.singletonList(new ArchiveSale());
    
    when(mockArchiveSaleRepository.findAllByInvoiceNumberAndUserId(invoiceNumber, userId)).thenReturn(expectedSales);
    
    List<ArchiveSale> result = toTest.findAllByInvoiceNumberAndUserId(invoiceNumber, userId);
    
    assertEquals(expectedSales, result);
    verify(mockArchiveSaleRepository).findAllByInvoiceNumberAndUserId(invoiceNumber, userId);
  }
  
  @Test
  void testDeleteAll() {
    List<ArchiveSale> archiveSales = Collections.singletonList(new ArchiveSale());
    
    toTest.deleteAll(archiveSales);
    
    verify(mockArchiveSaleRepository).deleteAll(archiveSales);
  }
}
