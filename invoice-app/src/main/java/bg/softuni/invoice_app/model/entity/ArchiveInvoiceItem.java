package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "archive_invoice_items")
public class ArchiveInvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal quantity;
    
    @Column(nullable = false)
    private BigDecimal unitPrice;
    
    @Column(nullable = false)
    private BigDecimal totalPrice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_invoice_id", nullable = false)
    private ArchiveInvoice archiveInvoice;
    
    public Long getId() {
        return id;
    }
    
    public ArchiveInvoiceItem setId(Long id) {
        this.id = id;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public ArchiveInvoiceItem setName(String name) {
        this.name = name;
        return this;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public ArchiveInvoiceItem setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public ArchiveInvoiceItem setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public ArchiveInvoiceItem setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
    
    public ArchiveInvoice getArchiveInvoice() {
        return archiveInvoice;
    }
    
    public ArchiveInvoiceItem setArchiveInvoice(ArchiveInvoice archiveInvoice) {
        this.archiveInvoice = archiveInvoice;
        return this;
    }
}