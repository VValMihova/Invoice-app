package bg.softuni.invoice_app.repository;

import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleName name);
}