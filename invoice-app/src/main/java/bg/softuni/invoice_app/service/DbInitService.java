package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.entity.*;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.repository.*;
import bg.softuni.invoice_app.service.role.RoleService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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
  private final ModelMapper modelMapper;
  private final InvoiceRepository invoiceRepository;
  private final BankAccountPersistRepository bankAccountPersistRepository;
  private final SaleRepository saleRepository;
  
  public DbInitService(
      UserRepository userRepository,
      CompanyDetailsRepository companyDetailsRepository,
      RoleService roleService, RoleRepository roleRepository,
      BankAccountRepository bankAccountRepository,
      RecipientDetailsRepository recipientDetailsRepository,
      PasswordEncoder passwordEncoder, ModelMapper modelMapper, InvoiceRepository invoiceRepository, BankAccountPersistRepository bankAccountPersistRepository, SaleRepository saleRepository) {
    this.userRepository = userRepository;
    this.companyDetailsRepository = companyDetailsRepository;
    this.roleService = roleService;
    this.roleRepository = roleRepository;
    this.bankAccountRepository = bankAccountRepository;
    this.recipientDetailsRepository = recipientDetailsRepository;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
    this.invoiceRepository = invoiceRepository;
    this.bankAccountPersistRepository = bankAccountPersistRepository;
    this.saleRepository = saleRepository;
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
      
      BankAccountPersist bankAccountPersist1 = modelMapper.map(bankAccount1, BankAccountPersist.class).setUser(user1);
      bankAccountPersistRepository.save(bankAccountPersist1);
      
      Invoice invoice1 = new Invoice()
          .setInvoiceNumber(Long.parseLong("1"))
          .setIssueDate(LocalDate.parse("2024-07-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
          .setRecipient(recipientDetails1)
          .setSupplier(companyDetails1)
          .setItems(List.of(new InvoiceItem()
                  .setQuantity(BigDecimal.valueOf(10))
                  .setName("Product1")
                  .setUnitPrice(BigDecimal.valueOf(100))
                  .setTotalPrice(BigDecimal.valueOf(1000.0))))
          .setBankAccountPersist(bankAccountPersist1)
          .setUser(user1)
          .setTotalAmount(BigDecimal.valueOf(1000.0))
          .setVat(BigDecimal.valueOf(200.0))
          .setAmountDue(BigDecimal.valueOf(1200.0));
      invoiceRepository.save(invoice1);
      
      Sale sale1 = new Sale()
          .setProductName("Product1")
          .setQuantity(BigDecimal.valueOf(10))
          .setSaleDate(LocalDate.parse("2024-07-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
          .setUser(user1)
          .setInvoiceId(Long.parseLong("1"));
      saleRepository.save(sale1);
      
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
