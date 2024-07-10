package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies_details")
public class CompanyDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String companyName;
  
  @Column(nullable = false)
  private String address;
  
  @Column(nullable = false, unique = true)
  private String eik;
  
  @Column(nullable = false, unique = true)
  private String vatNumber;
  
  @Column(nullable = false)
  private String manager;
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "company_details_id")
  private Set<BankAccount> bankAccounts;
  
  public CompanyDetails() {
    this.bankAccounts = new HashSet<>();
  }
  
  public Long getId() {
    return id;
  }
  
  public CompanyDetails setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getCompanyName() {
    return companyName;
  }
  
  public CompanyDetails setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getAddress() {
    return address;
  }
  
  public CompanyDetails setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public CompanyDetails setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public CompanyDetails setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public CompanyDetails setManager(String manager) {
    this.manager = manager;
    return this;
  }
  
  public Set<BankAccount> getBankAccounts() {
    return bankAccounts;
  }
  
  public CompanyDetails setBankAccounts(Set<BankAccount> bankAccounts) {
    this.bankAccounts = bankAccounts;
    return this;
  }
  
  public void addBankAccount(BankAccount bankAccount) {
    this.bankAccounts.add(bankAccount);
  }
}
