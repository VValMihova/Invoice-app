package bg.softuni.invoice_app.service.role;

import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

  @Mock
  private RoleRepository mockRoleRepository;
  
  private RoleService toTest;
  
  @BeforeEach
  public void setUp() {
    this.toTest = new RoleServiceImpl(mockRoleRepository);
  }
  
  @Test
  public void testGetRole() {
    Role testRole = new Role().setName(RoleName.ADMIN);
    
    when(mockRoleRepository.findByName(RoleName.ADMIN)).thenReturn(Optional.of(testRole));
    
    Optional<Role> foundRole = toTest.getRole(RoleName.ADMIN);
    
    assertTrue(foundRole.isPresent());
    assertEquals(RoleName.ADMIN, foundRole.get().getName());
  }
  
  @Test
  public void testFindByName_NotFound() {
    Role testRole = new Role();
    
    when(mockRoleRepository.findByName(testRole.getName())).thenReturn(Optional.empty());
    
    Optional<Role> foundRole = toTest.getRole(testRole.getName());
    
    assertTrue(foundRole.isEmpty());
  }
}