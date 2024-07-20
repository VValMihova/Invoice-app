package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.dto.ReportCriteria;
import bg.softuni.invoice_app.model.dto.SaleReportDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.entity.Invoice;
import bg.softuni.invoice_app.model.entity.Sale;
import bg.softuni.invoice_app.repository.SaleRepository;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
  private final SaleRepository saleRepository;
  private final InvoiceService invoiceService;
  
  public SaleServiceImpl(SaleRepository saleRepository, InvoiceService invoiceService) {
    this.saleRepository = saleRepository;
    this.invoiceService = invoiceService;
  }
  
  @Override
  public void save(Sale sale) {
    this.saleRepository.save(sale);
  }
  
  @Override
  public void deleteAllByInvoiceId(Long id) {
    InvoiceView invoice = invoiceService.getById(id);
    this.saleRepository.deleteAllByInvoiceId(invoice.getId());
  }
  
  @Override
  public List<SaleReportDto> generateReport(ReportCriteria criteria) {
    return saleRepository.findSalesReport(criteria.getStartDate(), criteria.getEndDate());
  }
}
