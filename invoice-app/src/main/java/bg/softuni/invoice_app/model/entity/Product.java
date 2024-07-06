package bg.softuni.invoice_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, unique = true)
  private String name;
  
  public Product() {
  }
  
  public Long getId() {
    return id;
  }
  
  public Product setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getName() {
    return name;
  }
  
  public Product setName(String name) {
    this.name = name;
    return this;
  }
}
