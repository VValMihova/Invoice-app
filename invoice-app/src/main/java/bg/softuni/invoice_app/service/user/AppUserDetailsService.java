package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;


public class AppUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
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
    return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities(user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList()))
        .build();
  }
}

