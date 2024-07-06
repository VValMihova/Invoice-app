package bg.softuni.invoice_app.model.entity;

import bg.softuni.invoice_app.model.enums.RoleName;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private RoleName name;
  
  public Role() {
  }
  
  public Long getId() {
    return id;
  }
  
  public Role setId(Long id) {
    this.id = id;
    return this;
  }
  
  public RoleName getName() {
    return name;
  }
  
  public Role setName(RoleName name) {
    this.name = name;
    return this;
  }
}
