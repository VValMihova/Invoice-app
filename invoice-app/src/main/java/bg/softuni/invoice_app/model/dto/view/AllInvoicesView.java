package bg.softuni.invoice_app.model.dto.view;

import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsEditBindingDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AllInvoicesView {
  private Long id;
  private Long invoiceNumber;
  private LocalDate issueDate;
  private CompanyDetailsEditBindingDto recipient;
  private BigDecimal amountDue;
  
  public AllInvoicesView() {
  }
  
  public Long getInvoiceNumber() {
    return invoiceNumber;
  }
  
  public AllInvoicesView setInvoiceNumber(Long invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
    return this;
  }
  
  public LocalDate getIssueDate() {
    return issueDate;
  }
  
  public AllInvoicesView setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
    return this;
  }
  
  public CompanyDetailsEditBindingDto getRecipient() {
    return recipient;
  }
  
  public AllInvoicesView setRecipient(CompanyDetailsEditBindingDto recipient) {
    this.recipient = recipient;
    return this;
  }
  
  public BigDecimal getAmountDue() {
    return amountDue;
  }
  
  public AllInvoicesView setAmountDue(BigDecimal amountDue) {
    this.amountDue = amountDue;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public AllInvoicesView setId(Long id) {
    this.id = id;
    return this;
  }
}
