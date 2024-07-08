package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String iban;
  
  @Column(nullable = false)
  private String bic;
  
  @Column(nullable = false)
  private String currency;
  
  public BankAccount() {
  }
  
  public BankAccount(String iban, String bic, String currency) {
    this.iban = iban;
    this.bic = bic;
    this.currency = currency;
  }
  
  public Long getId() {
    return id;
  }
  
  public BankAccount setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getIban() {
    return iban;
  }
  
  public BankAccount setIban(String iban) {
    this.iban = iban;
    return this;
  }
  
  public String getBic() {
    return bic;
  }
  
  public BankAccount setBic(String bic) {
    this.bic = bic;
    return this;
  }
  
  public String getCurrency() {
    return currency;
  }
  
  public BankAccount setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
}
