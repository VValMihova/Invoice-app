package bg.softuni.invoice_app.service.arcive;

import bg.softuni.invoice_app.exeption.ArchiveInvoiceNotFoundException;
import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.InvoiceNotFoundException;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceService;
import bg.softuni.invoice_app.service.archive.ArchiveSaleService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.service.sale.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArchiveInvoiceServiceTest {
  
  @Mock
  private ArchiveInvoiceRepository mockArchiveInvoiceRepository;
  @Mock
  private InvoiceService mockInvoiceService;
  @Mock
  private ArchiveSaleService mockArchiveSaleService;
  @Mock
  private SaleService mockSaleService;
  
  @InjectMocks
  private ArchiveInvoiceService toTest;
  
  private ArchiveInvoice archiveInvoice;
  private Invoice invoice;
  
  @BeforeEach
  void setUp() {
    archiveInvoice = new ArchiveInvoice();
    archiveInvoice.setId(TEST_ID);
    archiveInvoice.setInvoiceNumber(INVOICE_NUMBER);
    archiveInvoice.setUser(new User().setId(TEST_ID_2));
    archiveInvoice.setItems(Collections.singletonList(new ArchiveInvoiceItem()));
    
    invoice = new Invoice();
    invoice.setId(TEST_ID);
    invoice.setInvoiceNumber(INVOICE_NUMBER);
    invoice.setUser(new User().setId(TEST_ID_2));
  }
  
  @Test
  void testRestoreInvoice_NotFound() {
    Long invoiceId = TEST_ID;
    when(mockArchiveInvoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());
    
    ArchiveInvoiceNotFoundException exception = assertThrows(ArchiveInvoiceNotFoundException.class, () -> toTest.restoreInvoice(invoiceId, TEST_ID));
    assertEquals(ErrorMessages.ARCHIVE_INVOICE_NOT_FOUND, exception.getMessage());
    
    verify(mockArchiveInvoiceRepository, times(1)).findById(invoiceId);
    verifyNoMoreInteractions(mockArchiveInvoiceRepository, mockInvoiceService, mockArchiveSaleService, mockSaleService);
  }
  
  @Test
  void testRestoreInvoice_Found() {
    Long invoiceId = TEST_ID;
    Long userId = TEST_ID_2;
    Long initialInvoiceNumber = INVOICE_NUMBER;
    Long newInvoiceNumber = 125L;
    
    ArchiveInvoice archiveInvoice = new ArchiveInvoice();
    archiveInvoice.setId(invoiceId);
    archiveInvoice.setInvoiceNumber(initialInvoiceNumber);
    archiveInvoice.setUser(new User().setId(userId));
    archiveInvoice.setItems(Collections.singletonList(new ArchiveInvoiceItem()));
    
    when(mockArchiveInvoiceRepository.findById(invoiceId)).thenReturn(Optional.of(archiveInvoice));
    when(mockInvoiceService.existsByInvoiceNumberAndUserId(initialInvoiceNumber, userId)).thenReturn(true);
    when(mockInvoiceService.findMaxInvoiceNumberByUserId(userId)).thenReturn(newInvoiceNumber);
    when(mockArchiveSaleService.findAllByInvoiceNumberAndUserId(newInvoiceNumber + 1, userId)).thenReturn(Collections.singletonList(new ArchiveSale())); // Актуализирайте с новия номер на фактурата
    
    toTest.restoreInvoice(invoiceId, userId);
    
    ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
    verify(mockInvoiceService).save(invoiceCaptor.capture());
    Invoice capturedInvoice = invoiceCaptor.getValue();
    
    assertEquals(newInvoiceNumber + 1, capturedInvoice.getInvoiceNumber());
    
    verify(mockArchiveInvoiceRepository).findById(invoiceId);
    verify(mockInvoiceService).existsByInvoiceNumberAndUserId(initialInvoiceNumber, userId);
    verify(mockInvoiceService).findMaxInvoiceNumberByUserId(userId);
    verify(mockArchiveInvoiceRepository).delete(archiveInvoice);
    
    ArgumentCaptor<Sale> saleCaptor = ArgumentCaptor.forClass(Sale.class);
    verify(mockSaleService).save(saleCaptor.capture());
    Sale capturedSale = saleCaptor.getValue();
    
    assertEquals(capturedInvoice.getInvoiceNumber(), capturedSale.getInvoiceNumber());
    
    verify(mockArchiveSaleService).deleteAll(any());
  }
  
  @Test
  void testFindAllByUserId() {
    Long userId = TEST_ID;
    Pageable pageable = Pageable.unpaged();
    Page<ArchiveInvoice> archiveInvoicesPage = Page.empty();
    
    when(mockArchiveInvoiceRepository.findAllByUserId(userId, pageable)).thenReturn(archiveInvoicesPage);
    
    Page<ArchiveInvoice> result = toTest.findAllByUserId(userId, pageable);
    
    assertEquals(archiveInvoicesPage, result);
    
    verify(mockArchiveInvoiceRepository).findAllByUserId(userId, pageable);
  }
  
  @Test
  void testExistsByBankAccount() {
    BankAccountPersist account = new BankAccountPersist();
    
    when(mockArchiveInvoiceRepository.existsByBankAccountPersist(account)).thenReturn(true);
    
    boolean result = toTest.existsByBankAccount(account);
    
    assertTrue(result);
    
    verify(mockArchiveInvoiceRepository).existsByBankAccountPersist(account);
  }
  
  @Test
  void testDelete() {
    ArchiveInvoice archiveInvoice = new ArchiveInvoice();
    archiveInvoice.setId(TEST_ID);
    
    when(mockArchiveInvoiceRepository.findById(TEST_ID)).thenReturn(Optional.of(archiveInvoice));
    
    toTest.delete(archiveInvoice);
    
    verify(mockArchiveInvoiceRepository).findById(TEST_ID);
    verify(mockArchiveInvoiceRepository).delete(archiveInvoice);
  }
  
  @Test
  void testDelete_NotFound() {
    ArchiveInvoice archiveInvoice = new ArchiveInvoice();
    archiveInvoice.setId(TEST_ID);
    
    when(mockArchiveInvoiceRepository.findById(TEST_ID)).thenReturn(Optional.empty());
    
    InvoiceNotFoundException exception = assertThrows(InvoiceNotFoundException.class, () -> toTest.delete(archiveInvoice));
    assertEquals(ErrorMessages.INVOICE_NOT_FOUND, exception.getMessage());
    
    verify(mockArchiveInvoiceRepository).findById(TEST_ID);
    verify(mockArchiveInvoiceRepository, never()).delete(any(ArchiveInvoice.class));
  }
  
  @Test
  void testFindOlderThanTwoMonths() {
    List<ArchiveInvoice> expectedList = Collections.singletonList(new ArchiveInvoice());
    LocalDateTime twoMonthsAgo = LocalDateTime.now().minusMonths(2);
    
    when(mockArchiveInvoiceRepository.findAllDeletedOlderThanTwoMonths(any(LocalDateTime.class))).thenReturn(expectedList);
    
    List<ArchiveInvoice> result = toTest.findOlderThanTwoMonths();
    
    assertEquals(expectedList, result);
    verify(mockArchiveInvoiceRepository).findAllDeletedOlderThanTwoMonths(any(LocalDateTime.class));
  }
  
  
}
