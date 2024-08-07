package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
  Invoice getById(Long id);
  
  List<Invoice> findAllByUserIdOrderByInvoiceNumber(Long id);
  
  @Query("SELECT i FROM Invoice i WHERE i.user.id = :userId AND i.invoiceNumber = :invoiceNumber")
  Optional<Invoice> findByUserIdAndInvoiceNumber(@Param("userId") Long userId, @Param("invoiceNumber") Long invoiceNumber);
  
  boolean existsByBankAccountPersist(BankAccountPersist account);
  
  boolean existsByInvoiceNumber(Long invoiceNumber);
  boolean existsByInvoiceNumberAndUserId(Long invoiceNumber, Long userId);
  
  @Query("SELECT MAX(i.invoiceNumber) FROM Invoice i")
  Long findMaxInvoiceNumber();
  
  @Query(
      "SELECT i FROM Invoice i WHERE i.user.id = :userId AND " +
      "(:recipient IS NULL OR i.recipient.companyName LIKE %:recipient%) AND " +
      "(:issueDate IS NULL OR i.issueDate = :issueDate) " +
      "ORDER BY i.invoiceNumber")
  List<Invoice> findAllByUserIdAndCriteria(
      @Param("userId") Long userId,
      @Param("recipient") String recipient,
      @Param("issueDate") LocalDate issueDate);
  
  @Query("SELECT MAX(i.invoiceNumber) FROM Invoice i WHERE i.user.id = :userId")
  Long findMaxInvoiceNumberByUserId(@Param("userId") Long userId);
}