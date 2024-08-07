package bg.softuni.invoice_app.service.archive;

import bg.softuni.invoice_app.repository.ArchiveInvoiceItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ArchiveInvoiceItemService {
  private final ArchiveInvoiceItemRepository archiveInvoiceItemRepository;
  
  public ArchiveInvoiceItemService(ArchiveInvoiceItemRepository archiveInvoiceItemRepository) {
    this.archiveInvoiceItemRepository = archiveInvoiceItemRepository;
  }
  @Transactional
  public void deleteAllByArchiveInvoiceId(Long id) {
    this.archiveInvoiceItemRepository.deleteAllByArchiveInvoiceId(id);
  }
}
