package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.binding.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.binding.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.dto.view.BankAccountViewDto;
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
  public void register(UserRegisterBindingDto registerData) {
    User user = registerUser(registerData);
    CompanyDetails companyDetails = createCompanyDetails(registerData);
    
    user.setCompanyDetails(companyDetails);
    
    this.companyDetailsService.addWithRegistration(companyDetails);
    this.userRepository.save(user);
  }
  
  private User registerUser(UserRegisterBindingDto registerData) {
    return new User()
        .setEmail(registerData.getEmail())
        .setPassword(passwordEncoder.encode(registerData.getPassword()));
  }
  
  private CompanyDetails createCompanyDetails(UserRegisterBindingDto registerData) {
    return  new CompanyDetails()
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
  public void updateCompany(CompanyDetailsDto companyData) {
    companyDetailsService
        .deleteCompany(userHelperService.getUser().getCompanyDetails().getId());
    
    userRepository.save(
        userHelperService.getUser()
            .setCompanyDetails(modelMapper.map(companyData, CompanyDetails.class)));
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
