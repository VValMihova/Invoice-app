package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.HashSet;
import java.util.Set;

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
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  private Set<Product> products;
  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Invoice> invoices;
  
  public User() {
    this.roles = new HashSet<>();
    this.products = new HashSet<>();
    this.invoices = new OrderedHashSet<>();
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
  
  public Set<Product> getProducts() {
    return products;
  }
  
  public User setProducts(Set<Product> products) {
    this.products = products;
    return this;
  }
  
  public Set<Invoice> getInvoices() {
    return invoices;
  }
  
  public User setInvoices(Set<Invoice> invoices) {
    this.invoices = invoices;
    return this;
  }
}
