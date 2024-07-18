package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
  BankAccount getById(Long id);
  
  Optional<BankAccount> findByIban(String iban);
  
  Optional<Set<BankAccount>> findByCompanyDetailsId(Long companyId);
}