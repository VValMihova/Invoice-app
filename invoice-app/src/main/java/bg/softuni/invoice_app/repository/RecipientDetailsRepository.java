package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.RecipientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipientDetailsRepository extends JpaRepository<RecipientDetails, Long> {
  Optional<RecipientDetails> findByEik(String eik);
  Optional<RecipientDetails> findByVatNumber(String vatNumber);
  
  @Query("SELECT r FROM RecipientDetails r WHERE r.vatNumber = :vatNumber AND r.user.id = :userId")
  Optional<RecipientDetails> findByVatNumberAndUserId(@Param("vatNumber") String vatNumber, @Param("userId") Long userId);
}