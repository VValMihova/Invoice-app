package bg.softuni.invoice_app.utils;

import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.service.bankAccountPersist.BankAccountPersistService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledJob {
  
  private final BankAccountPersistService bankAccountPersistService;
  private final InvoiceService invoiceService;
  
  public ScheduledJob(BankAccountPersistService bankAccountPersistService, InvoiceService invoiceService) {
    this.bankAccountPersistService = bankAccountPersistService;
    this.invoiceService = invoiceService;
  }
  
  @Transactional
  @Scheduled(cron = "0 0 0 * * ?")
  public void cleanupUnusedBankAccounts() {
    List<BankAccountPersist> allPersistantAccounts = bankAccountPersistService.getAllPersistantAccounts();
    
    for (BankAccountPersist account : allPersistantAccounts) {
      boolean isUsed = invoiceService.existsByBankAccount(account);
      if (!isUsed) {
        bankAccountPersistService.delete(account);
      }
    }
  }
}

