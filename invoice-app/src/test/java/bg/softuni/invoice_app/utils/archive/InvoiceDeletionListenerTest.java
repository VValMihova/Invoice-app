package bg.softuni.invoice_app.utils.archive;

import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import bg.softuni.invoice_app.repository.SaleRepository;
import bg.softuni.invoice_app.service.sale.SaleService;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceDeletionListenerTest {
  
  @Mock
  private ArchiveInvoiceRepository mockArchiveInvoiceRepository;
  @Mock
  private SaleService mockSaleService;
  @Mock
  private ArchiveSaleRepository mockArchiveSaleRepository;
  @Mock
  private UserService mockUserService;
  @Mock
  private SaleRepository mockSaleRepository;
  
  @InjectMocks
  private InvoiceDeletionListener toTest;
  
  @Captor
  private ArgumentCaptor<ArchiveInvoice> archiveInvoiceCaptor;
  
  
  @Test
  void testOnApplicationEvent() {
    Invoice invoice = new Invoice();
    invoice.setId(TEST_ID);
    invoice.setInvoiceNumber(INVOICE_NUMBER);
    invoice.setIssueDate(TEST_DATE_NOW);
    invoice.setSupplier(new CompanyDetails());
    invoice.setRecipient(new RecipientDetails());
    invoice.setTotalAmount(INVOICE_TOTAL_AMOUNT);
    invoice.setVat(INVOICE_VAT);
    invoice.setAmountDue(INVOICE_AMOUNT_DUE);
    invoice.setBankAccountPersist(new BankAccountPersist());
    invoice.setUser(new User().setId(TEST_ID_2));
    invoice.setItems(Collections.singletonList(new InvoiceItem()));
    
    InvoiceDeletedEvent event = new InvoiceDeletedEvent(this, invoice);
    
    when(mockUserService.getCurrentUserId()).thenReturn(TEST_ID_2);
    when(mockSaleService.findAllByInvoiceNumber(INVOICE_NUMBER, TEST_ID_2))
        .thenReturn(Collections.singletonList(new Sale().setUser(new User().setId(TEST_ID_2))));
    
    toTest.onApplicationEvent(event);
    
    verify(mockUserService).getCurrentUserId();
    verify(mockSaleService).findAllByInvoiceNumber(INVOICE_NUMBER, TEST_ID_2);
    verify(mockArchiveSaleRepository).save(any(ArchiveSale.class));
    verify(mockSaleRepository).delete(any(Sale.class));
    
    verify(mockArchiveInvoiceRepository).save(archiveInvoiceCaptor.capture());
    ArchiveInvoice capturedArchiveInvoice = archiveInvoiceCaptor.getValue();
    
    assertEquals(INVOICE_NUMBER, capturedArchiveInvoice.getInvoiceNumber());
    assertEquals(TEST_DATE_NOW, capturedArchiveInvoice.getIssueDate());
    assertEquals(invoice.getSupplier(), capturedArchiveInvoice.getSupplier());
    assertEquals(invoice.getRecipient(), capturedArchiveInvoice.getRecipient());
    assertEquals(INVOICE_TOTAL_AMOUNT, capturedArchiveInvoice.getTotalAmount());
    assertEquals(INVOICE_VAT, capturedArchiveInvoice.getVat());
    assertEquals(INVOICE_AMOUNT_DUE, capturedArchiveInvoice.getAmountDue());
    assertEquals(invoice.getBankAccountPersist(), capturedArchiveInvoice.getBankAccountPersist());
    assertEquals(invoice.getUser(), capturedArchiveInvoice.getUser());
  }
}