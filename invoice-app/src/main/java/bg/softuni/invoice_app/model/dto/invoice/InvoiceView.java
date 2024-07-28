package bg.softuni.invoice_app.model.dto.invoice;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InvoiceView {
  private Long id;
  private Long invoiceNumber;
  private LocalDate issueDate;
  private CompanyDetailsView supplier;
  private RecipientDetailsView recipient;
  private List<InvoiceItemView> items;
  private BigDecimal totalAmount;
  private BigDecimal vat;
  private BigDecimal amountDue;
  private BankAccountPersist bankAccount;
  
  public InvoiceView() {
  }
  public InvoiceView(Invoice invoice) {
    this.id = invoice.getId();
    this.invoiceNumber = invoice.getInvoiceNumber();
    this.issueDate = invoice.getIssueDate();
    this.supplier = new CompanyDetailsView(invoice.getSupplier());
    this.recipient = new RecipientDetailsView(invoice.getRecipient());
    this.items = invoice.getItems().stream().map(InvoiceItemView::new).collect(toList());
    this.totalAmount = invoice.getTotalAmount();
    this.vat = invoice.getVat();
    this.amountDue = invoice.getAmountDue();
    this.bankAccount = invoice.getBankAccountPersist();
  }
  
  public BigDecimal getAmountDue() {
    return amountDue;
  }
  
  public InvoiceView setAmountDue(BigDecimal amountDue) {
    this.amountDue = amountDue;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public InvoiceView setId(Long id) {
    this.id = id;
    return this;
  }
  
  public Long getInvoiceNumber() {
    return invoiceNumber;
  }
  
  public InvoiceView setInvoiceNumber(Long invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
    return this;
  }
  
  public LocalDate getIssueDate() {
    return issueDate;
  }
  
  public InvoiceView setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
    return this;
  }
  
  public List<InvoiceItemView> getItems() {
    return items;
  }
  
  public InvoiceView setItems(List<InvoiceItemView> items) {
    this.items = items;
    return this;
  }
  
  public RecipientDetailsView getRecipient() {
    return recipient;
  }
  
  public InvoiceView setRecipient(RecipientDetailsView recipient) {
    this.recipient = recipient;
    return this;
  }
  
  public CompanyDetailsView getSupplier() {
    return supplier;
  }
  
  public InvoiceView setSupplier(CompanyDetailsView supplier) {
    this.supplier = supplier;
    return this;
  }
  
  public BigDecimal getTotalAmount() {
    return totalAmount;
  }
  
  public InvoiceView setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }
  
  public BigDecimal getVat() {
    return vat;
  }
  
  public InvoiceView setVat(BigDecimal vat) {
    this.vat = vat;
    return this;
  }
  
  public BankAccountPersist getBankAccountPersist() {
    return bankAccount;
  }
  
  public InvoiceView setBankAccountPersist(BankAccountPersist bankAccountPersist) {
    this.bankAccount = bankAccountPersist;
    return this;
  }
}
