package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.entity.Invoice;


import java.util.Optional;

public interface InvoicesService {
  Optional<Invoice> findById(Long id);
  
  void deleteById(Long id);
  
  byte[] generatePdf(Long id);
  
  void update(Long id, InvoiceCreateDto invoiceData);
}
