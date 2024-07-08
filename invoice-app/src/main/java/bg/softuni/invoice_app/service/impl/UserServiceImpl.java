package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.binding.BankAccountCreateDto;
import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.binding.UserRegisterDto;
import bg.softuni.invoice_app.model.dto.binding.invoice.BankAccountDto;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.CompanyDetailsService;
import bg.softuni.invoice_app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;
  private final UserHelperService userHelperService;
  private final CompanyDetailsService companyDetailsService;
  
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserHelperService userHelperService, CompanyDetailsService companyDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
    this.userHelperService = userHelperService;
    this.companyDetailsService = companyDetailsService;
  }
  
  
  @Override
  public void register(UserRegisterDto registerData) {
    User user = createUser(registerData);
    this.userRepository.save(user);
  }
  
  private User createUser(UserRegisterDto registerData) {
    return new User()
        .setEmail(registerData.getEmail())
        .setPassword(passwordEncoder.encode(registerData.getPassword()))
        .setCompanyDetails(modelMapper.map(registerData.getCompanyDetails(), CompanyDetails.class));
  }
  
  @Override
  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }
  
  @Override
  public void updateCompany(CompanyDetailsDto companyData) {
    companyDetailsService
        .deleteCompany(userHelperService.getUser().getCompanyDetails().getId());
    
    userRepository.save(
        userHelperService.getUser()
            .setCompanyDetails(modelMapper.map(companyData, CompanyDetails.class)));
  }
  
  @Override
  public void addBankAccount(BankAccountCreateDto bankAccountData) {
    User user = userHelperService.getUser();
    CompanyDetails companyDetails = user.getCompanyDetails();
    BankAccount bankAccount = modelMapper.map(bankAccountData, BankAccount.class);
    companyDetails.addBankAccount(bankAccount);
    
    user.setCompanyDetails(companyDetails);
    this.userRepository.save(user);
    
  }
  
  @Override
  public List<BankAccountDto> getAllBankAccounts() {
    return
        userHelperService.getUser()
            .getCompanyDetails().getBankAccounts()
            .stream()
            .map(account -> modelMapper.map(account, BankAccountDto.class))
            .toList();
  }
  
}
