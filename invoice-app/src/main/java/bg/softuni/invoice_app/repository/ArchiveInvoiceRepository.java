package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.ArchiveInvoice;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ArchiveInvoiceRepository extends JpaRepository<ArchiveInvoice, Long> {
  Page<ArchiveInvoice> findAllByUserId(Long userId, Pageable pageable);
  
  boolean existsByBankAccountPersist(BankAccountPersist account);
  
  @Query("SELECT ai FROM ArchiveInvoice ai WHERE ai.deletedAt < :twoMonthsAgo")
  List<ArchiveInvoice> findAllDeletedOlderThanTwoMonths(
      @Param("twoMonthsAgo") LocalDateTime twoMonthsAgo);
}