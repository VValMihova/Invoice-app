package bg.softuni.invoice_app.model.dto.invoice;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceEditDto {
  private Long id;
  
  @NotNull(message = "{invoice.number.not.null}")
  @Positive(message = "{invoice.number.positive}")
  private Long invoiceNumber;
  
  @NotNull(message = "{invoice.issue.date.not.null}")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate issueDate;
  
  private RecipientDetailsView recipient;
  
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
  
  public RecipientDetailsView getRecipient() {
    return recipient;
  }
  
  public InvoiceEditDto setRecipient(RecipientDetailsView recipient) {
    this.recipient = recipient;
    return this;
  }
  
  
  public Long getId() {
    return id;
  }
  
  public InvoiceEditDto setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getBankAccountIban() {
    return bankAccountIban;
  }
  
  public InvoiceEditDto setBankAccountIban(String bankAccountIban) {
    this.bankAccountIban = bankAccountIban;
    return this;
  }
}
