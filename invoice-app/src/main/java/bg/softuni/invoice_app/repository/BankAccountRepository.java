package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
  Optional<BankAccount> findByIban(String iban);
}