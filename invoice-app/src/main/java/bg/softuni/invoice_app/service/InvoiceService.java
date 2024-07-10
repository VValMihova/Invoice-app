package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.invoice.AllInvoicesView;

import java.util.List;

public interface InvoiceService {
 // void create(InvoiceCreateDto invoiceData);
  List<AllInvoicesView> getAllInvoices();
}
