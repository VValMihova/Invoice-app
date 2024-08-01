package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.model.entity.Sale;

import java.util.List;

public interface SaleService {
  void save(Sale sale);
  
  void deleteAllByInvoiceId(Long id);
  
  List<SaleReportDto> generateReport(ReportCriteria reportCriteria);
  
  List<Sale> findAllByInvoiceId(Long id);
}
