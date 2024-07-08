package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.binding.invoice.BankAccountDto;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserHelperService {
  private static final String ROLE_PREFIX = "ROLE_";
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  
  public UserHelperService(UserRepository userRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }
  
  public User getUser() {
    return userRepository
        .findByEmail(getUserDetails().getUsername())
        .orElse(null);
  }
  
  public boolean hasRole(String role) {
    return getUserDetails()
        .getAuthorities()
        .stream()
        .anyMatch(r -> r.getAuthority().equals(ROLE_PREFIX + role));
  }
  
  public UserDetails getUserDetails() {
    return (UserDetails) getAuthentication().getPrincipal();
  }
  
  public boolean isAuthenticated() {
    return !hasRole("ANONYMOUS");
  }
  
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
  
  public CompanyDetailsDto getCompanyDetails(){
    return this.modelMapper.map(this.getUser().getCompanyDetails(), CompanyDetailsDto.class);
  }
  
  public Set<BankAccount> getBankAccounts(){
    return this.getUser().getCompanyDetails().getBankAccounts();
  }
}
