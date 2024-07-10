package bg.softuni.invoice_app.model.dto.binding.invoice;

import bg.softuni.invoice_app.model.dto.view.BankAccountViewDto;
import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceCreateDto {
  private Long invoiceNumber;
  private LocalDate issueDate;
  private CompanyDetailsDto supplier;
  private CompanyDetailsDto recipient;
  private BankAccountViewDto bankAccount;
  private List<InvoiceItemDto> items;
  private BigDecimal totalAmount;
  private BigDecimal vat;
  private BigDecimal amountDue;
  
  public Long getInvoiceNumber() {
    return invoiceNumber;
  }
  
  public InvoiceCreateDto setInvoiceNumber(Long invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
    return this;
  }
  
  public LocalDate getIssueDate() {
    return issueDate;
  }
  
  public InvoiceCreateDto setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
    return this;
  }
  
  public CompanyDetailsDto getSupplier() {
    return supplier;
  }
  
  public InvoiceCreateDto setSupplier(CompanyDetailsDto supplier) {
    this.supplier = supplier;
    return this;
  }
  
  public CompanyDetailsDto getRecipient() {
    return recipient;
  }
  
  public InvoiceCreateDto setRecipient(CompanyDetailsDto recipient) {
    this.recipient = recipient;
    return this;
  }
  
  public BankAccountViewDto getBankAccount() {
    return bankAccount;
  }
  
  public InvoiceCreateDto setBankAccount(BankAccountViewDto bankAccount) {
    this.bankAccount = bankAccount;
    return this;
  }
  
  public List<InvoiceItemDto> getItems() {
    return items;
  }
  
  public InvoiceCreateDto setItems(List<InvoiceItemDto> items) {
    this.items = items;
    return this;
  }
  
  public BigDecimal getTotalAmount() {
    return totalAmount;
  }
  
  public InvoiceCreateDto setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }
  
  public BigDecimal getVat() {
    return vat;
  }
  
  public InvoiceCreateDto setVat(BigDecimal vat) {
    this.vat = vat;
    return this;
  }
  
  public BigDecimal getAmountDue() {
    return amountDue;
  }
  
  public InvoiceCreateDto setAmountDue(BigDecimal amountDue) {
    this.amountDue = amountDue;
    return this;
  }
}
