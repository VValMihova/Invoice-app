package bg.softuni.invoice_app.utils;

import bg.softuni.invoice_app.model.entity.ArchiveInvoice;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceItemService;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceService;
import bg.softuni.invoice_app.service.archive.ArchiveSaleService;
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
  private final ArchiveInvoiceService archiveInvoiceService;
  private final ArchiveSaleService archiveSaleService;
  private final ArchiveInvoiceItemService archiveInvoiceItemService;
  
  public ScheduledJob(BankAccountPersistService bankAccountPersistService, InvoiceService invoiceService, ArchiveInvoiceService archiveInvoiceService, ArchiveSaleService archiveSaleService, ArchiveInvoiceItemService archiveInvoiceItemService) {
    this.bankAccountPersistService = bankAccountPersistService;
    this.invoiceService = invoiceService;
    this.archiveInvoiceService = archiveInvoiceService;
    this.archiveSaleService = archiveSaleService;
    
    this.archiveInvoiceItemService = archiveInvoiceItemService;
  }
  
  @Transactional
  @Scheduled(cron = "0 0 0 * * ?")
  public void cleanupUnusedBankAccounts() {
    List<BankAccountPersist> allPersistantAccounts = bankAccountPersistService.getAllPersistantAccounts();
    
    for (BankAccountPersist account : allPersistantAccounts) {
      boolean isUsed = invoiceService.existsByBankAccount(account);
      boolean isUsedInArchive = archiveInvoiceService.existsByBankAccount(account);
      if (!isUsed && !isUsedInArchive) {
        bankAccountPersistService.delete(account);
      }
    }
  }
  
  @Transactional
  @Scheduled(cron = "0 0 0 * * ?")
  public void cleanupOldArchive() {
    List<ArchiveInvoice> olderThanTwoMonths = archiveInvoiceService.findOlderThanTwoMonths();
    
    for (ArchiveInvoice invoice : olderThanTwoMonths) {
      archiveSaleService.deleteArchiveSales(invoice.getInvoiceNumber(), invoice.getUser().getId());
      archiveInvoiceService.delete(invoice);
      archiveInvoiceItemService.deleteAllByArchiveInvoiceId(invoice.getId());
    }
  }
}

