package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_accouns_persistant")
public class BankAccountPersist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String iban;
  
  @Column(nullable = false)
  private String bic;
  
  @Column(nullable = false)
  private String currency;
  
  @ManyToOne(optional = false)
  private User user;
  
  public String getBic() {
    return bic;
  }
  
  public BankAccountPersist setBic(String bic) {
    this.bic = bic;
    return this;
  }
  
  public String getCurrency() {
    return currency;
  }
  
  public BankAccountPersist setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
  
  public String getIban() {
    return iban;
  }
  
  public BankAccountPersist setIban(String iban) {
    this.iban = iban;
    return this;
  }
  
  public Long getId() {
    return id;
  }
  
  public BankAccountPersist setId(Long id) {
    this.id = id;
    return this;
  }
  
  public User getUser() {
    return user;
  }
  
  public BankAccountPersist setUser(User user) {
    this.user = user;
    return this;
  }
}
