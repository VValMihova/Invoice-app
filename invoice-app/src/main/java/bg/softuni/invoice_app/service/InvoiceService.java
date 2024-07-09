package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.view.AllInvoicesView;

import java.util.List;

public interface InvoiceService {
  void create(InvoiceCreateDto invoiceData);
  List<AllInvoicesView> getAllInvoices();
}
