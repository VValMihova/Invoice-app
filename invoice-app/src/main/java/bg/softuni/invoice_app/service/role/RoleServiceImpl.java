package bg.softuni.invoice_app.service.role;

import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }
  
  @Override
  public Optional<Role> getRole(RoleName roleName) {
    return roleRepository.findByName(roleName);
  }
}
