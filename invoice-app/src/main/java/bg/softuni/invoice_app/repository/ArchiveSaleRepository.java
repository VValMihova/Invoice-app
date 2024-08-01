package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.ArchiveSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchiveSaleRepository extends JpaRepository<ArchiveSale, Long> {
  List<ArchiveSale> findAllByInvoiceId(Long invoiceId);
}