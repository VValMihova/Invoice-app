package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.ArchiveInvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveInvoiceItemRepository extends JpaRepository<ArchiveInvoiceItem, Long> {
  void deleteAllByArchiveInvoiceId(Long id);
}