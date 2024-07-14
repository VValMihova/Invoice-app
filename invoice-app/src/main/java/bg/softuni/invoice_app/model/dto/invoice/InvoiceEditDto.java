package bg.softuni.invoice_app.model.dto.invoice;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountDto;
import bg.softuni.invoice_app.model.dto.companyDetails.GetCompanyDetailsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceEditDto {
  @NotNull(message = "{invoice.number.not.null}")
  @Positive(message = "{invoice.number.positive}")
  private Long invoiceNumber;
  
  @NotNull(message = "{invoice.issue.date.not.null}")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate issueDate;
  
  @Valid
  private GetCompanyDetailsDto supplier;
  
  @Valid
  private RecipientDetailsAddDto recipient;
  
  @NotBlank(message = "{invoice.bank.account.not.null}")
  private String bankAccountIban;
  
  @Valid
  private List<InvoiceItemDto> items;
  
  @NotNull(message = "{invoice.total.amount.not.null}")
  @Positive(message = "{invoice.total.amount.positive}")
  private BigDecimal totalAmount;
  
  @NotNull(message = "{invoice.vat.not.null}")
  @Positive(message = "{invoice.vat.positive}")
  private BigDecimal vat;
  
  @NotNull(message = "{invoice.amount.due.not.null}")
  @Positive(message = "{invoice.amount.positive}")
  private BigDecimal amountDue;
  
  public Long getInvoiceNumber() {
    return invoiceNumber;
  }
  
  public InvoiceEditDto setInvoiceNumber(Long invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
    return this;
  }
  
  public LocalDate getIssueDate() {
    return issueDate;
  }
  
  public InvoiceEditDto setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
    return this;
  }
  
  public GetCompanyDetailsDto getSupplier() {
    return supplier;
  }
  
  public InvoiceEditDto setSupplier(GetCompanyDetailsDto supplier) {
    this.supplier = supplier;
    return this;
  }
  
  public String getBankAccount() {
    return bankAccountIban;
  }
  
  public InvoiceEditDto setBankAccount(String bankAccountIban ) {
    this.bankAccountIban = bankAccountIban;
    return this;
  }
  
  public List<InvoiceItemDto> getItems() {
    return items;
  }
  
  public InvoiceEditDto setItems(List<InvoiceItemDto> items) {
    this.items = items;
    return this;
  }
  
  public BigDecimal getTotalAmount() {
    return totalAmount;
  }
  
  public InvoiceEditDto setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }
  
  public BigDecimal getVat() {
    return vat;
  }
  
  public InvoiceEditDto setVat(BigDecimal vat) {
    this.vat = vat;
    return this;
  }
  
  public BigDecimal getAmountDue() {
    return amountDue;
  }
  
  public InvoiceEditDto setAmountDue(BigDecimal amountDue) {
    this.amountDue = amountDue;
    return this;
  }
  
  public @Valid RecipientDetailsAddDto getRecipient() {
    return recipient;
  }
  
  public InvoiceEditDto setRecipient(@Valid RecipientDetailsAddDto recipient) {
    this.recipient = recipient;
    return this;
  }
}
