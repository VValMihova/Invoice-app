package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
  void deleteAllByInvoiceId(Long id);
  List<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
  
  @Query("SELECT new bg.softuni.invoice_app.model.dto.sale.SaleReportDto(s.productName, SUM(s.quantity)) " +
         "FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate GROUP BY s.productName" +
         " ORDER BY s.productName DESC ")
  List<SaleReportDto> findSalesReport(LocalDate startDate, LocalDate endDate);
}