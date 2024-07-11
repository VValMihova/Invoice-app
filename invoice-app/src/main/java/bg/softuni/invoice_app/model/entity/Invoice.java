package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {
//  todo can be with uuid
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, unique = true)
  private Long invoiceNumber;
  
  @Column(nullable = false)
  private LocalDate issueDate;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "supplier_id", nullable = false)
  private CompanyDetails supplier;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "recipient_id", nullable = false)
  private RecipientDetails recipient;
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "invoice_id")
  private List<InvoiceItem> items;
  
  @Column(nullable = false)
  private BigDecimal totalAmount;
  
  @Column(nullable = false)
  private BigDecimal vat;
  
  @Column(nullable = false)
  private BigDecimal amountDue;
  
  @ManyToOne(optional = true)
  private BankAccount bankAccount;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  public Invoice() {
  this.items = new ArrayList<>();
  }
  
  
  public Long getId() {
    return id;
  }
  
  public Invoice setId(Long id) {
    this.id = id;
    return this;
  }
  
  public Long getInvoiceNumber() {
    return invoiceNumber;
  }
  
  public Invoice setInvoiceNumber(Long invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
    return this;
  }
  
  public LocalDate getIssueDate() {
    return issueDate;
  }
  
  public Invoice setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
    return this;
  }
  
  public CompanyDetails getSupplier() {
    return supplier;
  }
  
  public Invoice setSupplier(CompanyDetails supplier) {
    this.supplier = supplier;
    return this;
  }
  
  public RecipientDetails getRecipient() {
    return recipient;
  }
  
  public Invoice setRecipient(RecipientDetails recipient) {
    this.recipient = recipient;
    return this;
  }
  
  public List<InvoiceItem> getItems() {
    return items;
  }
  
  public Invoice setItems(List<InvoiceItem> items) {
    this.items = items;
    return this;
  }
  
  public BigDecimal getTotalAmount() {
    return totalAmount;
  }
  
  public Invoice setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }
  
  public BigDecimal getVat() {
    return vat;
  }
  
  public Invoice setVat(BigDecimal vat) {
    this.vat = vat;
    return this;
  }
  
  public BigDecimal getAmountDue() {
    return amountDue;
  }
  
  public Invoice setAmountDue(BigDecimal amountDue) {
    this.amountDue = amountDue;
    return this;
  }
  
  public User getUser() {
    return user;
  }
  
  public Invoice setUser(User user) {
    this.user = user;
    return this;
  }
  
  public BankAccount getBankAccount() {
    return bankAccount;
  }
  
  public Invoice setBankAccount(BankAccount bankAccount) {
    this.bankAccount = bankAccount;
    return this;
  }
}
