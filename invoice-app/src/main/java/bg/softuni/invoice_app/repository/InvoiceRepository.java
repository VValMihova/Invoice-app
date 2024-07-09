package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
  List<Invoice> findAllByUserId(Long id);
}