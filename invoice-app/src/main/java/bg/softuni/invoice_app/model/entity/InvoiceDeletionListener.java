package bg.softuni.invoice_app.model.entity;

import bg.softuni.invoice_app.repository.ArchiveInvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceDeletionListener implements ApplicationListener<InvoiceDeletedEvent> {
  
  private static final Logger logger = LoggerFactory.getLogger(InvoiceDeletionListener.class);
  
  @Autowired
  private ArchiveInvoiceRepository archiveInvoiceRepository;
  
  @Override
  @Transactional
  public void onApplicationEvent(InvoiceDeletedEvent event) {
    Invoice invoice = event.getInvoice();
    
    logger.info("Starting to archive invoice with ID: {}", invoice.getId());
    
    // Архивиране на фактурата
    ArchiveInvoice archiveInvoice = new ArchiveInvoice();
    archiveInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
    archiveInvoice.setIssueDate(invoice.getIssueDate());
    archiveInvoice.setSupplier(invoice.getSupplier());
    archiveInvoice.setRecipient(invoice.getRecipient());
    archiveInvoice.setTotalAmount(invoice.getTotalAmount());
    archiveInvoice.setVat(invoice.getVat());
    archiveInvoice.setAmountDue(invoice.getAmountDue());
    archiveInvoice.setBankAccountPersist(invoice.getBankAccountPersist());
    archiveInvoice.setUser(invoice.getUser());
    
    List<ArchiveInvoiceItem> archiveItems = new ArrayList<>();
    for (InvoiceItem item : invoice.getItems()) {
      ArchiveInvoiceItem archiveItem = new ArchiveInvoiceItem();
      archiveItem.setName(item.getName());
      archiveItem.setQuantity(item.getQuantity());
      archiveItem.setUnitPrice(item.getUnitPrice());
      archiveItem.setTotalPrice(item.getTotalPrice());
      archiveItem.setArchiveInvoice(archiveInvoice);
      archiveItems.add(archiveItem);
    }
    archiveInvoice.setItems(archiveItems);
    archiveInvoice.setDeletedAt(LocalDateTime.now());
    archiveInvoiceRepository.save(archiveInvoice);
    logger.info("Archived invoice with ID: {}", archiveInvoice.getId());
  }
}