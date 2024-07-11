package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.entity.Invoice;


import java.util.Optional;

public interface InvoicesService {
  Optional<Invoice> findById(Long id);
  
  void deleteById(Long id);
  
  byte[] generatePdf(Long id);
  
  void updateInvoice(Long id, InvoiceEditDto invoiceData);
}
