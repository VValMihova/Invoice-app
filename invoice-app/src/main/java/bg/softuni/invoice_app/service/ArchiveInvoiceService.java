package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.entity.ArchiveInvoice;
import bg.softuni.invoice_app.model.entity.ArchiveInvoiceItem;
import bg.softuni.invoice_app.model.entity.Invoice;
import bg.softuni.invoice_app.model.entity.InvoiceItem;
import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import bg.softuni.invoice_app.repository.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArchiveInvoiceService {
  
  @Autowired
  private ArchiveInvoiceRepository archiveInvoiceRepository;
  
  @Autowired
  private InvoiceRepository invoiceRepository;
  
  @Transactional
  public void restoreInvoice(Long invoiceId) {
    ArchiveInvoice archiveInvoice = archiveInvoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new EntityNotFoundException("Archive Invoice not found"));
    
    // Възстановяване на фактурата
    Invoice invoice = new Invoice();
    invoice.setInvoiceNumber(archiveInvoice.getInvoiceNumber());
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
    
    invoiceRepository.save(invoice);
    
    // Изтриване на архивната фактура
    archiveInvoiceRepository.delete(archiveInvoice);
  }
  
  @Transactional
  public Page<ArchiveInvoice> findAllByUserId(Long userId, Pageable pageable) {
    return archiveInvoiceRepository.findAllByUserId(userId, pageable);
  }
}
