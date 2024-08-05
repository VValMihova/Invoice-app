package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.OrderedHashSet;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.sql.Types.VARCHAR;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, unique = true)
  private String email;
  
  @Column(nullable = false)
  private String password;
  
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "company_details_id", referencedColumnName = "id")
  private CompanyDetails companyDetails;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;
  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Invoice> invoices;
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  private Set<RecipientDetails> recipients;
  
  @UuidGenerator
  @JdbcTypeCode(VARCHAR)
  private String uuid;
  
  
  
  public User() {
    this.roles = new HashSet<>();
    this.invoices = new OrderedHashSet<>();
    this.roles = new HashSet<>();
  }
  
  public User(UUID userUuid) {
    this();
    this.uuid = userUuid.toString();
  }
  
  public Long getId() {
    return id;
  }
  
  public User setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getEmail() {
    return email;
  }
  
  public User setEmail(String email) {
    this.email = email;
    return this;
  }
  
  public String getPassword() {
    return password;
  }
  
  public User setPassword(String password) {
    this.password = password;
    return this;
  }
  
  public CompanyDetails getCompanyDetails() {
    return companyDetails;
  }
  
  public User setCompanyDetails(CompanyDetails companyDetails) {
    this.companyDetails = companyDetails;
    return this;
  }
  
  public Set<Role> getRoles() {
    return roles;
  }
  
  public User setRoles(Set<Role> roles) {
    this.roles = roles;
    return this;
  }
  
  public Set<Invoice> getInvoices() {
    return invoices;
  }
  
  public User setInvoices(Set<Invoice> invoices) {
    this.invoices = invoices;
    return this;
  }
  
  public Set<RecipientDetails> getRecipients() {
    return recipients;
  }
  
  public User setRecipients(Set<RecipientDetails> recipients) {
    this.recipients = recipients;
    return this;
  }
  
  public String getUuid() {
    return uuid;
  }
  
  public User setUuid(String uuid) {
    this.uuid = uuid;
    return this;
  }
}
