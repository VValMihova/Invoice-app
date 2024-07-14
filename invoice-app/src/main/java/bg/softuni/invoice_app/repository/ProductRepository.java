package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.Product;
import bg.softuni.invoice_app.model.entity.RecipientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<List<Product>> findAllByUserId(Long userId);
}