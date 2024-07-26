package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.model.user.InvoiceAppUserDetails;
import bg.softuni.invoice_app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class AppUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
  
  private static final String ROLE_PREFIX = "ROLE_";
  private final UserRepository userRepository;
  
  public AppUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .map(this::mapToUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(
            "User with email " + email + " not found!"));
  }
  
  private UserDetails mapToUserDetails(User user) {
    return new InvoiceAppUserDetails(
        user.getUuid(),
        user.getEmail(),
        user.getPassword(),
        user.getRoles().stream().map(Role::getName).map(this::map).toList());
  }
  
  
  private GrantedAuthority map(RoleName role) {
    return new SimpleGrantedAuthority(ROLE_PREFIX + role);
  }
}

