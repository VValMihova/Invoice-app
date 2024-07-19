package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.dto.ReportCriteria;
import bg.softuni.invoice_app.model.dto.SaleReportDto;
import bg.softuni.invoice_app.model.entity.Sale;

import java.util.List;

public interface SaleService {
  void save(Sale sale);
  
  void deleteAllByInvoiceId(Long id);
  
  List<Sale> getSalesReport(ReportCriteria reportCriteria);
  
  List<SaleReportDto> generateReport(ReportCriteria reportCriteria);
}
