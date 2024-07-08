package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.invoice.InvoiceCreateDto;

public interface InvoiceService {
  void create(InvoiceCreateDto invoiceData);
}
