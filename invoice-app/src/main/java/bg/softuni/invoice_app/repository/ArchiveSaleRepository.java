package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.ArchiveSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArchiveSaleRepository extends JpaRepository<ArchiveSale, Long> {
  List<ArchiveSale> findAllByInvoiceNumberAndUserId(Long invoiceId, Long userId);
}