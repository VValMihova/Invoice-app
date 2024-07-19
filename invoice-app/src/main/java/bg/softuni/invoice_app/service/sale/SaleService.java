package bg.softuni.invoice_app.service.sale;

import bg.softuni.invoice_app.model.entity.Sale;

public interface SaleService {
  void save(Sale sale);
  
  void deleteAllByInvoiceId(Long id);
}
