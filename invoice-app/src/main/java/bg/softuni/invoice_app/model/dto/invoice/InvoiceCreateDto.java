package bg.softuni.invoice_app.model.dto.invoice;

import bg.softuni.invoice_app.model.dto.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountViewDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.GetCompanyDetailsDto;
import bg.softuni.invoice_app.validation.UniqueInvoiceNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceCreateDto {
  
  @UniqueInvoiceNumber
  @NotNull(message = "{invoice.number.not.null}")
  @Positive(message = "{invoice.number.positive}")
  private Long invoiceNumber;
  
  @NotNull(message = "{invoice.issue.date.not.null}")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate issueDate;
  
  @Valid
  private GetCompanyDetailsDto supplier;
  
  @Valid
  private RecipientDetailsAddDto recipientDetails;
  
  // todo
  @Valid
  private BankAccountDto bankAccount;
  
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
  
  public GetCompanyDetailsDto getSupplier() {
    return supplier;
  }
  
  public InvoiceCreateDto setSupplier(GetCompanyDetailsDto supplier) {
    this.supplier = supplier;
    return this;
  }
  
  public @Valid RecipientDetailsAddDto getRecipientDetails() {
    return recipientDetails;
  }
  
  public InvoiceCreateDto setRecipientDetails(@Valid RecipientDetailsAddDto recipientDetails) {
    this.recipientDetails = recipientDetails;
    return this;
  }
  
  public BankAccountDto getBankAccount() {
    return bankAccount;
  }
  
  public InvoiceCreateDto setBankAccount(BankAccountDto bankAccount) {
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
