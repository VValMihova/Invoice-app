package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.invoice.AllInvoicesView;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;

import java.util.List;

public interface InvoiceService {
  List<AllInvoicesView> getAllInvoices();
  
  void createInvoice(InvoiceCreateDto invoiceData);
  
  boolean checkInvoiceExists(Long invoiceNumber);
}
