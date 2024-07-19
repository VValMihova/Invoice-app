package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.entity.Sale;
import bg.softuni.invoice_app.repository.SaleRepository;
import org.springframework.stereotype.Service;

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
  
  @Override
  public void deleteAllByInvoiceId(Long id) {
    this.saleRepository.deleteAllByInvoiceId(id);
  }
}
