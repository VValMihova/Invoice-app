package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipient_details")
public class RecipientDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, unique = true)
  private String companyName;
  
  @Column(nullable = false)
  private String address;
  
  @Column(nullable = false, unique = true)
  private String eik;
  
  @Column(nullable = false, unique = true)
  private String vatNumber;
  
  @Column(nullable = false)
  private String manager;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  public Long getId() {
    return id;
  }
  
  public RecipientDetails setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getCompanyName() {
    return companyName;
  }
  
  public RecipientDetails setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getAddress() {
    return address;
  }
  
  public RecipientDetails setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public RecipientDetails setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public RecipientDetails setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public RecipientDetails setManager(String manager) {
    this.manager = manager;
    return this;
  }
  
  public User getUser() {
    return user;
  }
  
  public RecipientDetails setUser(User user) {
    this.user = user;
    return this;
  }
}
