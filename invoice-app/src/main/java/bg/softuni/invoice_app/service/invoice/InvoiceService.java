package bg.softuni.invoice_app.service.invoice;

import bg.softuni.invoice_app.model.dto.invoice.AllInvoicesView;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceCreateDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
  List<AllInvoicesView> getAllInvoices();
  
  void updateInvoice(Long id, InvoiceEditDto invoiceData);
  
  boolean checkInvoiceExists(Long invoiceNumber);
  
  void deleteById(Long id);
  
  InvoiceView getById(Long id);
  
  void createInvoiceWithClient(Long clientId, InvoiceCreateDto invoiceData);
  
  InvoiceEditDto convertToEditDto(InvoiceView invoiceView);
  
  boolean existsByBankAccount(BankAccountPersist account);
  
  List<AllInvoicesView> searchInvoices(String recipient, LocalDate issueDate);
}
