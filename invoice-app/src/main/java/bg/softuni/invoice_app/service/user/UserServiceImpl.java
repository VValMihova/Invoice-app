package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.exeption.DatabaseException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;
  private final UserHelperService userHelperService;
  private final CompanyDetailsService companyDetailsService;
  
  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      ModelMapper modelMapper,
      UserHelperService userHelperService,
      CompanyDetailsService companyDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
    this.userHelperService = userHelperService;
    this.companyDetailsService = companyDetailsService;
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
  
  //  todo can be changed
  private User registerUser(UserRegisterBindingDto registerData) {
    return userRepository.save(new User()
        .setEmail(registerData.getEmail())
        .setPassword(passwordEncoder.encode(registerData.getPassword())));
  }
  
  private CompanyDetails createCompanyDetails(UserRegisterBindingDto registerData, User user) {
    return new CompanyDetails()
        .setCompanyName(registerData.getCompanyName())
        .setAddress(registerData.getAddress())
        .setEik(registerData.getEik())
        .setVatNumber(registerData.getVatNumber())
        .setManager(registerData.getManager());
  }
  
  @Override
  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }
  
  @Override
  public void updateCompany(CompanyDetails companyDetails) {
    User user = userHelperService.getUser();
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
  
}
