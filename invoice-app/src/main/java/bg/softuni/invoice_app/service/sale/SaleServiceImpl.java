package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.dto.report.ReportCriteria;
import bg.softuni.invoice_app.model.dto.sale.SaleReportDto;
import bg.softuni.invoice_app.model.entity.Sale;
import bg.softuni.invoice_app.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
  private final SaleRepository saleRepository;
  
  public SaleServiceImpl(SaleRepository saleRepository) {
    this.saleRepository = saleRepository;
  }
  
  @Override
  public void save(Sale sale) {
    this.saleRepository.save(sale);
  }
  
  @Transactional
  @Override
  public void deleteAllByInvoiceId(Long id) {
    this.saleRepository.deleteAllByInvoiceId(id);
  }
  
  @Override
  public List<SaleReportDto> generateReport(ReportCriteria criteria) {
    return saleRepository.findSalesReport(criteria.getStartDate(), criteria.getEndDate());
  }
  
  @Override
  public List<Sale> findAllByInvoiceId(Long id) {
    return saleRepository.findAllByInvoiceId(id);
  }
}
