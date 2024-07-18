package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.RecipientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipientDetailsRepository extends JpaRepository<RecipientDetails, Long> {
  Optional<RecipientDetails> findByVatNumber(String vatNumber);
  
  @Query("SELECT r FROM RecipientDetails r WHERE r.eik = :eik AND r.user.id = :userId")
  Optional<RecipientDetails> findByEikAndUserId(@Param("eik") String eik, @Param("userId") Long userId);

  Optional<List<RecipientDetails>> findAllByUserId(Long userId);
  
  boolean existsByCompanyName(String companyName);
  
  boolean existsByVatNumber(String vat);
  
  boolean existsByEik(String eik);
  
}