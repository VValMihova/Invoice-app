package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
  Optional<CompanyDetails> findByEik(String eik);
}