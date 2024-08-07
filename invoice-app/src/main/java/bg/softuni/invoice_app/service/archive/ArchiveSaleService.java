package bg.softuni.invoice_app.service.archive;

import bg.softuni.invoice_app.model.entity.ArchiveSale;
import bg.softuni.invoice_app.repository.ArchiveSaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
  
  public List<ArchiveSale> findAllByInvoiceNumberAndUserId(Long invoiceNumber, Long userId) {
    return archiveSaleRepository.findAllByInvoiceNumberAndUserId(invoiceNumber, userId);
  }
  @Transactional
  public void deleteAll(List<ArchiveSale> archiveSales) {
    archiveSaleRepository.deleteAll(archiveSales);
  }
}
