package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountViewDto;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.CompanyDetailsRepository;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.CompanyDetailsService;
import bg.softuni.invoice_app.service.UserService;
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
  // todo remove after
  private final CompanyDetailsRepository companyDetailsRepository;
  
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserHelperService userHelperService, CompanyDetailsService companyDetailsService, CompanyDetailsRepository companyDetailsRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
    this.userHelperService = userHelperService;
    this.companyDetailsService = companyDetailsService;
    this.companyDetailsRepository = companyDetailsRepository;
  }
  
  
  @Override
  public void register(UserRegisterBindingDto registerData) {
    User user = registerUser(registerData);
    CompanyDetails companyDetails = createCompanyDetails(registerData, user);
    
    user.setCompanyDetails(companyDetails);
    
    this.companyDetailsService.addWithRegistration(companyDetails);
    this.userRepository.save(user);
  }
//  todo can be changed
  private User registerUser(UserRegisterBindingDto registerData) {
    return userRepository.save( new User()
        .setEmail(registerData.getEmail())
        .setPassword(passwordEncoder.encode(registerData.getPassword())));
  }
  
  private CompanyDetails createCompanyDetails(UserRegisterBindingDto registerData, User user) {
    return new CompanyDetails()
        .setCompanyName(registerData.getCompanyName())
        .setAddress(registerData.getAddress())
        .setEik(registerData.getEik())
        .setVatNumber(registerData.getVatNumber())
        .setManager(registerData.getManager())
        .setUser(user);
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
  public void addBankAccount(BankAccountCreateBindingDto bankAccountData) {
    User user = userHelperService.getUser();
    CompanyDetails companyDetails = user.getCompanyDetails();
    BankAccount bankAccount = modelMapper.map(bankAccountData, BankAccount.class);
    companyDetails.addBankAccount(bankAccount);
    
    user.setCompanyDetails(companyDetails);
    this.userRepository.save(user);
  }
  
  @Override
  public List<BankAccountViewDto> getAllBankAccounts() {
    return
        userHelperService.getUser()
            .getCompanyDetails().getBankAccounts()
            .stream()
            .map(account -> modelMapper.map(account, BankAccountViewDto.class))
            .toList();
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
