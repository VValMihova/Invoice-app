package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  
  Optional<User> findByCompanyDetailsEik(String eik);
  
  Optional<User> findByCompanyDetailsVatNumber(String vat);
  
  User getById(Long id);
  
  Page<User> findAllByEmailNot(String currentUserEmail, PageRequest pageRequest);
}