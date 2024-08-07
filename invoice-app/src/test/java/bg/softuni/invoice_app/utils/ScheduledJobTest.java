package bg.softuni.invoice_app.utils;

import bg.softuni.invoice_app.model.entity.ArchiveInvoice;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceService;
import bg.softuni.invoice_app.service.archive.ArchiveSaleService;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceItemService;
import bg.softuni.invoice_app.service.bankAccountPersist.BankAccountPersistService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledJobTest {
  
  @Mock
  private BankAccountPersistService mockBankAccountPersistService;
  
  @Mock
  private InvoiceService mockInvoiceService;
  
  @Mock
  private ArchiveInvoiceService mockArchiveInvoiceService;
  
  @Mock
  private ArchiveSaleService mockArchiveSaleService;
  
  @Mock
  private ArchiveInvoiceItemService mockArchiveInvoiceItemService;
  
  @InjectMocks
  private ScheduledJob toTest;
  
  private BankAccountPersist usedAccount;
  private BankAccountPersist unusedAccount;
  private ArchiveInvoice oldInvoice;
  
  @BeforeEach
  void setUp() {
    usedAccount = new BankAccountPersist();
    unusedAccount = new BankAccountPersist();
    
    oldInvoice = new ArchiveInvoice();
    oldInvoice.setId(TEST_ID);
    oldInvoice.setInvoiceNumber(INVOICE_NUMBER);
    oldInvoice.setUser(new User().setId(TEST_ID_2));
  }
  
  @Test
  void testCleanupUnusedBankAccounts() {
    when(mockBankAccountPersistService.getAllPersistantAccounts()).thenReturn(Arrays.asList(usedAccount, unusedAccount));
    when(mockInvoiceService.existsByBankAccount(usedAccount)).thenReturn(true);
    when(mockInvoiceService.existsByBankAccount(unusedAccount)).thenReturn(false);
    when(mockArchiveInvoiceService.existsByBankAccount(usedAccount)).thenReturn(false);
    when(mockArchiveInvoiceService.existsByBankAccount(unusedAccount)).thenReturn(false);
    
    toTest.cleanupUnusedBankAccounts();
    
    verify(mockBankAccountPersistService).getAllPersistantAccounts();
    verify(mockInvoiceService).existsByBankAccount(usedAccount);
    verify(mockInvoiceService).existsByBankAccount(unusedAccount);
    verify(mockArchiveInvoiceService).existsByBankAccount(usedAccount);
    verify(mockArchiveInvoiceService).existsByBankAccount(unusedAccount);
    verify(mockBankAccountPersistService).delete(unusedAccount);
    verify(mockBankAccountPersistService, never()).delete(usedAccount);
  }
  
  @Test
  void testCleanupOldArchive() {
    when(mockArchiveInvoiceService.findOlderThanTwoMonths()).thenReturn(Collections.singletonList(oldInvoice));
    
    toTest.cleanupOldArchive();
    
    verify(mockArchiveInvoiceService).findOlderThanTwoMonths();
    verify(mockArchiveSaleService).deleteArchiveSales(oldInvoice.getInvoiceNumber(), oldInvoice.getUser().getId());
    verify(mockArchiveInvoiceService).delete(oldInvoice);
    verify(mockArchiveInvoiceItemService).deleteAllByArchiveInvoiceId(oldInvoice.getId());
  }
}
