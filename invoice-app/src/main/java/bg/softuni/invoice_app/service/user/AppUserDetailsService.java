package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
            "User with " + email + " not found!"));
    
  }
  
  private UserDetails mapToUserDetails(User user) {
    return org.springframework.security.core.userdetails.User
        
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities(mapToGrantedAuthorities(user))
        .build();
  }
  
  private List<GrantedAuthority> mapToGrantedAuthorities(User user) {
    return user.getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
        .collect(Collectors.toList());
  }
}
