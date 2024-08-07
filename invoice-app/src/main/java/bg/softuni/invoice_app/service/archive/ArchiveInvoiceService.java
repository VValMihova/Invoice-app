package bg.softuni.invoice_app.service.archive;

import bg.softuni.invoice_app.exeption.ArchiveInvoiceNotFoundException;
import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.InvoiceNotFoundException;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.service.sale.SaleService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchiveInvoiceService {
  private final ArchiveInvoiceRepository archiveInvoiceRepository;
  private final InvoiceService invoiceService;
  private final ArchiveSaleService archiveSaleService;
  private final SaleService saleService;
  
  public ArchiveInvoiceService(ArchiveInvoiceRepository archiveInvoiceRepository, InvoiceService invoiceService, ArchiveSaleService archiveSaleService, SaleService saleService) {
    this.archiveInvoiceRepository = archiveInvoiceRepository;
    this.invoiceService = invoiceService;
    this.archiveSaleService = archiveSaleService;
    this.saleService = saleService;
  }
  
  @Transactional
  public void restoreInvoice(Long invoiceId, Long userId) {
    ArchiveInvoice archiveInvoice = archiveInvoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new ArchiveInvoiceNotFoundException(ErrorMessages.ARCHIVE_INVOICE_NOT_FOUND));
    
    Long invoiceNumber = archiveInvoice.getInvoiceNumber();
    if (invoiceService.existsByInvoiceNumberAndUserId(invoiceNumber, userId)) {
      Long maxInvoiceNumber = invoiceService.findMaxInvoiceNumberByUserId(userId);
      invoiceNumber = (maxInvoiceNumber != null) ? maxInvoiceNumber + 1L : 1L;
    }
    
    Invoice invoice = getInvoice(invoiceNumber, archiveInvoice);
    
    invoiceService.save(invoice);
    
    List<ArchiveSale> archiveSales = archiveSaleService.findAllByInvoiceNumberAndUserId(invoiceNumber, userId);
    for (ArchiveSale archiveSale : archiveSales) {
      Sale sale = new Sale();
      sale.setProductName(archiveSale.getProductName());
      sale.setQuantity(archiveSale.getQuantity());
      sale.setSaleDate(archiveSale.getSaleDate());
      sale.setInvoiceNumber(invoice.getInvoiceNumber());
      sale.setUser(invoice.getUser());
      saleService.save(sale);
    }
    
    archiveInvoiceRepository.delete(archiveInvoice);
    archiveSaleService.deleteAll(archiveSales);
  }
  
  private static Invoice getInvoice(Long invoiceNumber, ArchiveInvoice archiveInvoice) {
    Invoice invoice = new Invoice();
    invoice.setInvoiceNumber(invoiceNumber);
    invoice.setIssueDate(archiveInvoice.getIssueDate());
    invoice.setSupplier(archiveInvoice.getSupplier());
    invoice.setRecipient(archiveInvoice.getRecipient());
    invoice.setTotalAmount(archiveInvoice.getTotalAmount());
    invoice.setVat(archiveInvoice.getVat());
    invoice.setAmountDue(archiveInvoice.getAmountDue());
    invoice.setBankAccountPersist(archiveInvoice.getBankAccountPersist());
    invoice.setUser(archiveInvoice.getUser());
    
    List<InvoiceItem> items = new ArrayList<>();
    for (ArchiveInvoiceItem archiveItem : archiveInvoice.getItems()) {
      InvoiceItem item = new InvoiceItem();
      item.setName(archiveItem.getName());
      item.setQuantity(archiveItem.getQuantity());
      item.setUnitPrice(archiveItem.getUnitPrice());
      item.setTotalPrice(archiveItem.getTotalPrice());
      item.setInvoice(invoice);
      items.add(item);
    }
    invoice.setItems(items);
    return invoice;
  }
  
  @Transactional
  public Page<ArchiveInvoice> findAllByUserId(Long userId, Pageable pageable) {
    return archiveInvoiceRepository.findAllByUserId(userId, pageable);
  }
  
  public boolean existsByBankAccount(BankAccountPersist account) {
    return this.archiveInvoiceRepository.existsByBankAccountPersist(account);
  }
  
  public List<ArchiveInvoice> findOlderThanTwoMonths() {{
      LocalDateTime twoMonthsAgo = LocalDateTime.now().minusMonths(2);
      return archiveInvoiceRepository.findAllDeletedOlderThanTwoMonths(twoMonthsAgo);
    }
  }
  
  public void delete(ArchiveInvoice invoice) {
    archiveInvoiceRepository.findById(invoice.getId())
        .orElseThrow(() -> new InvoiceNotFoundException(ErrorMessages.INVOICE_NOT_FOUND));
    archiveInvoiceRepository.delete(invoice);
  }
}
