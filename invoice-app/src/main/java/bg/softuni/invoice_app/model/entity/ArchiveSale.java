package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "archive_sales")
public class ArchiveSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String productName;
    
    @Column(nullable = false)
    private BigDecimal quantity;
    
    @Column(nullable = false)
    private LocalDate saleDate;
    
    @Column(nullable = false)
    private Long invoiceId;
    
    public Long getId() {
        return id;
    }
    
    public ArchiveSale setId(Long id) {
        this.id = id;
        return this;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public ArchiveSale setProductName(String productName) {
        this.productName = productName;
        return this;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public ArchiveSale setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }
    
    public LocalDate getSaleDate() {
        return saleDate;
    }
    
    public ArchiveSale setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
        return this;
    }
    
    public Long getInvoiceId() {
        return invoiceId;
    }
    
    public ArchiveSale setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }
}
