package bg.softuni.invoiceappbankaccounts.repository;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
  BankAccountView getByIban(String iban);
  
  Optional<BankAccount> findByIban(String iban);
  
  boolean existsByIban(String iban);
}