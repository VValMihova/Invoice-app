package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.Product;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      return (UserDetails) principal;
    } else {
      throw new IllegalStateException("Principal is not an instance of UserDetails");
    }
  }
  
  public boolean isAuthenticated() {
    return !hasRole("ANONYMOUS");
  }
  
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
  
  public CompanyDetailsView getCompanyDetails() {
    return this.modelMapper.map(this.getUser().getCompanyDetails(), CompanyDetailsView.class);
  }

  //todo change with method up there
  public CompanyDetails getUserCompanyDetails() {
    return this.getUser().getCompanyDetails();
  }
  
  public Set<Product> getUserProducts() {
    return this.getUser().getProducts();
  }
}
