package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static bg.softuni.invoice_app.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest {
  
  @Mock
  private UserRepository mockUserRepository;
  
  @InjectMocks
  private AppUserDetailsService toTest;
  
  private User user;
  
  @BeforeEach
  void setUp() {
    toTest = new AppUserDetailsService(mockUserRepository);
    
    Role userRole = new Role().setName(RoleName.USER);
    Set<Role> roles = new HashSet<>();
    roles.add(userRole);
    
    user = new User();
    user.setId(TEST_ID);
    user.setEmail(TEST_EMAIL);
    user.setPassword(TEST_PASSWORD);
    user.setRoles(roles);
    user.setUuid(UUID.randomUUID().toString());
  }
  
  @Test
  void testLoadUserByUsername_UserFound() {
    when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    
    UserDetails userDetails = toTest.loadUserByUsername(user.getEmail());
    
    assertNotNull(userDetails);
    assertEquals(user.getEmail(), userDetails.getUsername());
    assertEquals(user.getPassword(), userDetails.getPassword());
    assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    
    verify(mockUserRepository).findByEmail(user.getEmail());
  }
  
  @Test
  void testLoadUserByUsername_UserNotFound() {
    String email = TEST_EMAIL;
    when(mockUserRepository.findByEmail(email)).thenReturn(Optional.empty());
    
    UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
      toTest.loadUserByUsername(email);
    });
    
    assertEquals("User with email " + email + " not found!", exception.getMessage());
    
    verify(mockUserRepository).findByEmail(email);
  }
}