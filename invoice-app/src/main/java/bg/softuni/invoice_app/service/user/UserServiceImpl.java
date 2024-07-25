package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.exeption.DatabaseException;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.model.user.InvoiceAppUserDetails;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.role.RoleService;
import bg.softuni.invoice_app.utils.InputFormating;
import bg.softuni.invoice_app.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;
  private final CompanyDetailsService companyDetailsService;
  private final RoleService roleService;
  
  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      ModelMapper modelMapper,
      CompanyDetailsService companyDetailsService, RoleService roleService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
    this.companyDetailsService = companyDetailsService;
    this.roleService = roleService;
  }
  
  
  @Override
  public void register(UserRegisterBindingDto registerData) {
    try {
      User user = registerUser(registerData);
      CompanyDetails companyDetails = createCompanyDetails(registerData, user);
      
      user.setCompanyDetails(companyDetails);
      
      this.companyDetailsService.addWithRegistration(companyDetails);
      this.userRepository.save(user);
    } catch (Exception e) {
      throw new DatabaseException("An error occurred while saving user to the database.");
    }
  }
  
  private User registerUser(UserRegisterBindingDto registerData) {
    return userRepository.save(new User()
        .setEmail(registerData.getEmail())
        .setPassword(passwordEncoder.encode(registerData.getPassword()))
        .setRoles(new HashSet<>(Set.of(roleService.getRole(RoleName.USER)))));
  }
  
  private CompanyDetails createCompanyDetails(UserRegisterBindingDto registerData, User user) {
    return new CompanyDetails()
        .setCompanyName(registerData.getCompanyName())
        .setAddress(registerData.getAddress())
        .setEik(registerData.getEik())
        .setVatNumber(InputFormating.format(registerData.getVatNumber()))
        .setManager(registerData.getManager());
  }
  
  @Override
  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }
  
  @Override
  public void updateCompany(CompanyDetails companyDetails) {
    User user = getUser();
    user.setCompanyDetails(companyDetails);
    this.userRepository.save(user);
  }
  
  @Override
  public User getUserByCompanyEik(String eik) {
    return this.userRepository.findByCompanyDetailsEik(eik).orElse(null);
  }
  
  @Override
  public User getUserByCompanyVat(String vat) {
    return this.userRepository.findByCompanyDetailsVatNumber(vat).orElse(null);
  }
  
  @Override
  public CompanyDetailsView showCompanyDetails() {
    return modelMapper.map(getCompanyDetails(), CompanyDetailsView.class);
  }
  
  @Override
  public CompanyDetails getCompanyDetails() {
    return userRepository.getById(getCurrentUserId()).getCompanyDetails();
  }
  
  @Override
  public Long getCurrentUserId() {
    Principal currentUser = SecurityUtils.getCurrentUser();
    if (currentUser == null) {
      return null;
    }
    User userByEmail = this.getUserByEmail(currentUser.getName());
    return userByEmail.getId();
  }
  
  //  todo add exception
  @Override
  public User getUser() {
    return this.userRepository.findById(getCurrentUserId()).orElse(null);
  }
  
  @Override
  public Optional<InvoiceAppUserDetails> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null
        && authentication.getPrincipal() instanceof InvoiceAppUserDetails invoiceAppUserDetails) {
      return Optional.of(invoiceAppUserDetails);
    }
    return Optional.empty();
  }
}
