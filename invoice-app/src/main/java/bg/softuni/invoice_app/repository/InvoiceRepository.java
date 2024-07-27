package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
  Invoice getById(Long id);
  
  List<Invoice> findAllByUserId(Long id);
  
  @Query("SELECT i FROM Invoice i WHERE i.user.id = :userId AND i.invoiceNumber = :invoiceNumber")
  Optional<Invoice> findByUserIdAndInvoiceNumber(@Param("userId") Long userId, @Param("invoiceNumber") Long invoiceNumber);
  
  boolean existsByBankAccountPersist(BankAccountPersist account);
}