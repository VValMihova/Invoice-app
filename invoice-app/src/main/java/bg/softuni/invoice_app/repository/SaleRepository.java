package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
  void deleteAllByInvoiceId(Long id);
}