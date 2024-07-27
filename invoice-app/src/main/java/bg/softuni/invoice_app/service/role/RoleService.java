package bg.softuni.invoice_app.service.role;

import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.enums.RoleName;

import java.util.Optional;

public interface RoleService {
  Optional<Role> getRole(RoleName roleName);
}
