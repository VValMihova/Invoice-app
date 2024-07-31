package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "archive_invoices")
public class ArchiveInvoice {
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
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "archiveInvoice")
    private List<ArchiveInvoiceItem> items;
    
    @Column(nullable = false)
    private BigDecimal totalAmount;
    
    @Column(nullable = false)
    private BigDecimal vat;
    
    @Column(nullable = false)
    private BigDecimal amountDue;
    
    @ManyToOne(optional = true)
    private BankAccountPersist bankAccountPersist;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDateTime deletedAt;
    
    public Long getId() {
        return id;
    }
    
    public ArchiveInvoice setId(Long id) {
        this.id = id;
        return this;
    }
    
    public Long getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public ArchiveInvoice setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }
    
    public LocalDate getIssueDate() {
        return issueDate;
    }
    
    public ArchiveInvoice setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }
    
    public CompanyDetails getSupplier() {
        return supplier;
    }
    
    public ArchiveInvoice setSupplier(CompanyDetails supplier) {
        this.supplier = supplier;
        return this;
    }
    
    public RecipientDetails getRecipient() {
        return recipient;
    }
    
    public ArchiveInvoice setRecipient(RecipientDetails recipient) {
        this.recipient = recipient;
        return this;
    }
    
    public List<ArchiveInvoiceItem> getItems() {
        return items;
    }
    
    public ArchiveInvoice setItems(List<ArchiveInvoiceItem> items) {
        this.items = items;
        return this;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public ArchiveInvoice setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
    
    public BigDecimal getVat() {
        return vat;
    }
    
    public ArchiveInvoice setVat(BigDecimal vat) {
        this.vat = vat;
        return this;
    }
    
    public BigDecimal getAmountDue() {
        return amountDue;
    }
    
    public ArchiveInvoice setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
        return this;
    }
    
    public BankAccountPersist getBankAccountPersist() {
        return bankAccountPersist;
    }
    
    public ArchiveInvoice setBankAccountPersist(BankAccountPersist bankAccountPersist) {
        this.bankAccountPersist = bankAccountPersist;
        return this;
    }
    
    public User getUser() {
        return user;
    }
    
    public ArchiveInvoice setUser(User user) {
        this.user = user;
        return this;
    }
    
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    
    public ArchiveInvoice setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}