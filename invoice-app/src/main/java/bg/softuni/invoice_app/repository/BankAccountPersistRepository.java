package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountPersistRepository extends JpaRepository<BankAccountPersist, Long> {
  boolean existsByIban(String iban);
  
  BankAccountPersist getByIban(String iban);
}