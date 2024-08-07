package bg.softuni.invoice_app.service.archive;

import bg.softuni.invoice_app.exeption.ArchiveInvoiceNotFoundException;
import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.InvoiceNotFoundException;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ArchiveInvoiceService {
  private final ArchiveInvoiceRepository archiveInvoiceRepository;
  private final InvoiceRepository invoiceRepository;
  private final ArchiveSaleRepository archiveSaleRepository;
  private final SaleRepository saleRepository;
  
  public ArchiveInvoiceService(ArchiveInvoiceRepository archiveInvoiceRepository, InvoiceRepository invoiceRepository, ArchiveSaleRepository archiveSaleRepository, SaleRepository saleRepository) {
    this.archiveInvoiceRepository = archiveInvoiceRepository;
    this.invoiceRepository = invoiceRepository;
    this.archiveSaleRepository = archiveSaleRepository;
    this.saleRepository = saleRepository;
  }
  
  @Transactional
  public void restoreInvoice(Long invoiceId, Long userId) {
    ArchiveInvoice archiveInvoice = archiveInvoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new ArchiveInvoiceNotFoundException(ErrorMessages.ARCHIVE_INVOICE_NOT_FOUND));
    
    Long invoiceNumber = archiveInvoice.getInvoiceNumber();
    if (invoiceRepository.existsByInvoiceNumber(invoiceNumber)) {
      Long maxInvoiceNumber = invoiceRepository.findMaxInvoiceNumber();
      invoiceNumber = (maxInvoiceNumber != null) ? maxInvoiceNumber + 1L : 1L;
    }
    
    Invoice invoice = getInvoice(invoiceNumber, archiveInvoice);
    
    invoiceRepository.save(invoice);
    
    List<ArchiveSale> archiveSales = archiveSaleRepository.findAllByInvoiceNumberAndUserId(invoiceNumber, userId);
    for (ArchiveSale archiveSale : archiveSales) {
      Sale sale = new Sale();
      sale.setProductName(archiveSale.getProductName());
      sale.setQuantity(archiveSale.getQuantity());
      sale.setSaleDate(archiveSale.getSaleDate());
      sale.setInvoiceNumber(invoice.getInvoiceNumber());
      sale.setUser(invoice.getUser());
      saleRepository.save(sale);
    }
    
    archiveInvoiceRepository.delete(archiveInvoice);
    archiveSaleRepository.deleteAll(archiveSales);
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
