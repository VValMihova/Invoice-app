package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.ArchiveInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveInvoiceRepository extends JpaRepository<ArchiveInvoice, Long> {
  Page<ArchiveInvoice> findAllByUserId(Long userId, Pageable pageable);
}