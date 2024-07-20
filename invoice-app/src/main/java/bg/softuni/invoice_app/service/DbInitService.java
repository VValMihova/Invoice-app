package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.repository.*;
import bg.softuni.invoice_app.service.role.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

@Service
public class DbInitService {
  private final UserRepository userRepository;
  private final CompanyDetailsRepository companyDetailsRepository;
  private final RoleService roleService;
  private final RoleRepository roleRepository;
  private final BankAccountRepository bankAccountRepository;
  private final RecipientDetailsRepository recipientDetailsRepository;
  private final PasswordEncoder passwordEncoder;
  
  public DbInitService(
      UserRepository userRepository,
      CompanyDetailsRepository companyDetailsRepository,
      RoleService roleService, RoleRepository roleRepository,
      BankAccountRepository bankAccountRepository,
      RecipientDetailsRepository recipientDetailsRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.companyDetailsRepository = companyDetailsRepository;
    this.roleService = roleService;
    this.roleRepository = roleRepository;
    this.bankAccountRepository = bankAccountRepository;
    this.recipientDetailsRepository = recipientDetailsRepository;
    this.passwordEncoder = passwordEncoder;
  }
  
  @PostConstruct
  private void initDb() {
    initRoles();
    initAdmin();
    initUsers();
  }
  
  private void initAdmin() {
    if (userRepository.count() == 0) {
      User admin = new User()
          .setEmail("admin@abv.bg")
          .setPassword(passwordEncoder.encode("11111"))
          .setRoles(
              Set.of(roleService.getRole(RoleName.ADMIN),
                  roleService.getRole(RoleName.USER)));
      userRepository.save(admin);
    }
  }
  
  private void initRoles() {
    if (roleRepository.count() == 0) {
      Arrays.stream(RoleName.values())
          .forEach(role ->
              roleRepository.save(new Role().setName(role)));
    }
  }
  
  private void initUsers() {
    if (userRepository.count() == 1 && companyDetailsRepository.count() == 0) {
      CompanyDetails companyDetails1 = new CompanyDetails()
          .setCompanyName("Company1")
          .setAddress("Address1")
          .setEik("1234567890")
          .setVatNumber("BG1234567890")
          .setManager("Manager1");
      
      User user1 = new User()
          .setEmail("test@abv.bg")
          .setPassword(this.passwordEncoder.encode("11111"))
          .setRoles(Set.of(roleService.getRole(RoleName.USER)))
          .setCompanyDetails(companyDetails1);
      
      userRepository.save(user1);
      
      RecipientDetails recipientDetails1 = new RecipientDetails()
          .setCompanyName("Recipient1")
          .setEik("1111111111")
          .setVatNumber("BG1111111111")
          .setAddress("Address1")
          .setManager("Manager1")
          .setUser(user1);
      
      recipientDetailsRepository.save(recipientDetails1);
      
      BankAccount bankAccount1 = new BankAccount()
          .setIban("BG80BNBG96611020345678")
          .setBic("UNCRITMM")
          .setCurrency("USD")
          .setCompanyDetails(companyDetails1);
      bankAccountRepository.save(bankAccount1);
      
      CompanyDetails companyDetails2 = new CompanyDetails()
          .setCompanyName("Company2")
          .setAddress("Address2")
          .setEik("1234567891")
          .setVatNumber("BG1234567891")
          .setManager("Manager2");
      
      User user2 = new User()
          .setEmail("test2@abv.bg")
          .setPassword(this.passwordEncoder.encode("22222"))
          .setRoles(Set.of(roleService.getRole(RoleName.USER)))
          .setCompanyDetails(companyDetails2);
      userRepository.save(user2);
      
      BankAccount bankAccount2 = new BankAccount()
          .setIban("BG80BNBG96611020345677")
          .setBic("UNCRITMM")
          .setCurrency("EUR")
          .setCompanyDetails(companyDetails2);
      bankAccountRepository.save(bankAccount2);
    }
  }
}
