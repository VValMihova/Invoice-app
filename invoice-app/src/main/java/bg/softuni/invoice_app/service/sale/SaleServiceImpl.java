package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.model.entity.Invoice;
import bg.softuni.invoice_app.model.entity.Sale;
import bg.softuni.invoice_app.repository.SaleRepository;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
  private final SaleRepository saleRepository;
  private final InvoiceService invoiceService;
  private final UserService userService;
  
  public SaleServiceImpl(SaleRepository saleRepository, @Lazy InvoiceService invoiceService, UserService userService) {
    this.saleRepository = saleRepository;
    this.invoiceService = invoiceService;
    this.userService = userService;
  }
  
  @Override
  public void save(Sale sale) {
    this.saleRepository.save(sale);
  }
  
  @Transactional
  @Override
  public void deleteAllByInvoiceId(Long id) {
    InvoiceView invoice = invoiceService.getById(id);
    this.saleRepository.deleteAllByInvoiceNumberAndUserId(invoice.getInvoiceNumber(), userService.getCurrentUserId());
  }
  
  @Override
  public List<SaleReportDto> generateReport(ReportCriteria criteria, Long userId) {
    return saleRepository.findSalesReport(criteria.getStartDate(), criteria.getEndDate(), userId);
  }
  
  @Override
  public List<Sale> findAllByInvoiceNumber(Long invoiceNumber, Long userId) {
    return saleRepository.findAllByInvoiceNumberAndUserId(invoiceNumber, userId);
  }
}
