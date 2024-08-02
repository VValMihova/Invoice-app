package bg.softuni.invoice_app.service.archive;

import bg.softuni.invoice_app.exeption.ArchiveInvoiceNotFoundException;
import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import bg.softuni.invoice_app.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArchiveInvoiceService {
//  todo change
  @Autowired
  private ArchiveInvoiceRepository archiveInvoiceRepository;
  
  @Autowired
  private InvoiceRepository invoiceRepository;
  
  @Autowired
  private ArchiveSaleRepository archiveSaleRepository;
  
  @Autowired
  private SaleRepository saleRepository;
  
  @Transactional
  public void restoreInvoice(Long invoiceId) {
    ArchiveInvoice archiveInvoice = archiveInvoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new ArchiveInvoiceNotFoundException(ErrorMessages.ARCHIVE_INVOICE_NOT_FOUND));
    
    Long invoiceNumber = archiveInvoice.getInvoiceNumber();
    if (invoiceRepository.existsByInvoiceNumber(invoiceNumber)) {
      Long maxInvoiceNumber = invoiceRepository.findMaxInvoiceNumber();
      invoiceNumber = (maxInvoiceNumber != null) ? maxInvoiceNumber + 1L : 1L;
    }
    
    Invoice invoice = getInvoice(invoiceNumber, archiveInvoice);
    
    invoiceRepository.save(invoice);
    
    List<ArchiveSale> archiveSales = archiveSaleRepository.findAllByInvoiceId(invoiceId);
    for (ArchiveSale archiveSale : archiveSales) {
      Sale sale = new Sale();
      sale.setProductName(archiveSale.getProductName());
      sale.setQuantity(archiveSale.getQuantity());
      sale.setSaleDate(archiveSale.getSaleDate());
      sale.setInvoiceId(invoice.getId());
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
}
