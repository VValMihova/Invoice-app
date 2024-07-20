package bg.softuni.invoice_app.service.role;

import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }
  //todo add exception
  @Override
  public Role getRole(RoleName roleName) {
    return roleRepository.findByName(roleName).orElse(null);
    // .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, "Role")));
  }
}
