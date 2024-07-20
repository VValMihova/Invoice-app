package bg.softuni.invoice_app.service.role;

import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.enums.RoleName;

public interface RoleService {
  Role getRole(RoleName roleName);
}
