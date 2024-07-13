package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.entity.Invoice;


import java.util.Optional;

public interface InvoicesService {
  
  void deleteById(Long id);
  
  byte[] generatePdf(Long id);
  
  void updateInvoice(Long id, InvoiceEditDto invoiceData);
  
  InvoiceView getById(Long id);
}
