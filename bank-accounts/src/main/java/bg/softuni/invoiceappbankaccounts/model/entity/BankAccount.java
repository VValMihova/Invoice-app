package bg.softuni.invoiceappbankaccounts.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  @Size(min = 15, max = 34)
  private String iban;
  
  @Column(nullable = false)
  @Size(min = 8, max = 11)
  private String bic;
  
  @Column(nullable = false)
  @Size(min = 3, max = 3)
  private String currency;
  
  @Column(nullable = false)
  private String user;
  
  public BankAccount() {
  }
  
  public BankAccount(String iban, String bic, String currency) {
    this.iban = iban;
    this.bic = bic;
    this.currency = currency;
    this.user = user;
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
  
  public String getUser() {
    return user;
  }
  
  public BankAccount setUser(String user) {
    this.user = user;
    return this;
  }
}
