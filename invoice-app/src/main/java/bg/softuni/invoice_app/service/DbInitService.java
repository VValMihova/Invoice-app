package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInitService {
  private final UserRepository userRepository;
  private final CompanyDetailsRepository companyDetailsRepository;
  private final InvoiceRepository invoiceRepository;
  private final BankAccountRepository bankAccountRepository;
  private final RecipientDetailsRepository recipientDetailsRepository;
  private final PasswordEncoder passwordEncoder;
  
  public DbInitService(UserRepository userRepository, CompanyDetailsRepository companyDetailsRepository, InvoiceRepository invoiceRepository, BankAccountRepository bankAccountRepository, RecipientDetailsRepository recipientDetailsRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.companyDetailsRepository = companyDetailsRepository;
    this.invoiceRepository = invoiceRepository;
    this.bankAccountRepository = bankAccountRepository;
    this.recipientDetailsRepository = recipientDetailsRepository;
    this.passwordEncoder = passwordEncoder;
  }
  
  @PostConstruct
  private void initDb() {
    initUsers();
  }
  
  private void initUsers() {
    if (userRepository.count() == 0 && companyDetailsRepository.count() == 0) {
      CompanyDetails companyDetails1 = new CompanyDetails()
          .setCompanyName("Company1")
          .setAddress("Address1")
          .setEik("1234567890")
          .setVatNumber("BG1234567890")
          .setManager("Manager1");
      
      User user1 = new User()
          .setEmail("test@abv.bg")
          .setPassword(this.passwordEncoder.encode("11111"))
          .setCompanyDetails(companyDetails1);
      userRepository.save(user1);
      
      CompanyDetails companyDetails2 = new CompanyDetails()
          .setCompanyName("Company2")
          .setAddress("Address2")
          .setEik("1234567891")
          .setVatNumber("BG1234567891")
          .setManager("Manager2");
      
      User user2 = new User()
          .setEmail("test2@abv.bg")
          .setPassword(this.passwordEncoder.encode("22222"))
          .setCompanyDetails(companyDetails2);
      userRepository.save(user2);
    }
  }
}
