package bg.softuni.invoice_app.service.archive;

import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ArchiveSaleService {
  private final ArchiveSaleRepository archiveSaleRepository;
  
  public ArchiveSaleService(ArchiveSaleRepository archiveSaleRepository) {
    this.archiveSaleRepository = archiveSaleRepository;
  }
  @Transactional
  public void deleteArchiveSales(Long invoiceNumber, Long id) {
    this.archiveSaleRepository.deleteAllByInvoiceNumberAndUserId(invoiceNumber, id);
  }
}
