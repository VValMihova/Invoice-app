package bg.softuni.invoice_app.util;

import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.service.bankAccountPersist.BankAccountPersistService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.utils.ScheduledJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledJobTest {
  
  @Mock
  private BankAccountPersistService mockBankAccountPersistService;
  
  @Mock
  private InvoiceService mockInvoiceService;
  
  @InjectMocks
  private ScheduledJob toTest;
  
  private BankAccountPersist usedAccount;
  private BankAccountPersist unusedAccount;
  
  @BeforeEach
  void setUp() {
    usedAccount = new BankAccountPersist();
    unusedAccount = new BankAccountPersist();
  }
  
  @Test
  void testCleanupUnusedBankAccounts() {
    when(mockBankAccountPersistService.getAllPersistantAccounts()).thenReturn(Arrays.asList(usedAccount, unusedAccount));
    when(mockInvoiceService.existsByBankAccount(usedAccount)).thenReturn(true);
    when(mockInvoiceService.existsByBankAccount(unusedAccount)).thenReturn(false);
    
    toTest.cleanupUnusedBankAccounts();
    
    verify(mockBankAccountPersistService).getAllPersistantAccounts();
    verify(mockInvoiceService).existsByBankAccount(usedAccount);
    verify(mockInvoiceService).existsByBankAccount(unusedAccount);
    verify(mockBankAccountPersistService).delete(unusedAccount);
    verify(mockBankAccountPersistService, never()).delete(usedAccount);
  }
}
