package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.BankAccountRepository;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.utils.InputFormating;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Set;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final BankAccountRepository bankAccountRepository;
  private final UserService userService;
  private final RestClient restClient;
  
  public BankAccountServiceImpl(
      BankAccountRepository bankAccountRepository,
      UserService userService, RestClient restClient) {
    this.bankAccountRepository = bankAccountRepository;
    this.userService = userService;
    this.restClient = restClient;
  }
  // todo connect
  @Override
  public BankAccountView getViewById(Long id) {
//    BankAccount bankAccount = getByIdOrElseThrow(id);
//
//    return mapToBankAccountView(bankAccount);
    return restClient
        .get()
        .uri("http://localhost:8081/bank-accounts/{id}", id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(BankAccountView.class);
  }
  
  @Override
  public BankAccount getByIban(String iban) {
    return this.bankAccountRepository.findByIban(iban)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
  }
  
  // todo connect
  @Override
  public Set<BankAccountView> findAllForCompany(Long companyId) {
//    Set<BankAccount> bankAccounts = this.bankAccountRepository.findByCompanyDetailsId(companyId)
//        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
//    if (bankAccounts.isEmpty()) {
//      return new HashSet<>();
//    } else {
//      return bankAccounts.stream()
//          .map(bankAccount -> this.modelMapper.map(bankAccount, BankAccountView.class))
//          .collect(Collectors.toSet());
//    }
    return restClient
        .get()
        .uri("http://localhost:8081/bank-accounts")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {
        });
  }
  
  @Override
  public void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit) {
    BankAccount bankAccount = getByIdOrElseThrow(id);
    
    bankAccount.setIban(InputFormating.format(bankAccountDataEdit.getIban()))
        .setBic(InputFormating.format(bankAccountDataEdit.getBic()))
        .setCurrency(InputFormating.format(bankAccountDataEdit.getCurrency()));
    
    bankAccountRepository.save(bankAccount);
  }
  
  
  @Override
  public void deleteBankAccount(Long id) {
    BankAccount bankAccount = getByIdOrElseThrow(id);
    this.bankAccountRepository.deleteById(id);
  }
  
  //  todo connect
  @Override
  public void addBankAccount(BankAccountCreateBindingDto bankAccountData) {
    restClient
        .post()
        .uri("http://localhost:8081/bank-accounts")
        .body(bankAccountData)
        .retrieve();
//    CompanyDetails companyDetails = userService.getCompanyDetails();
//    this.bankAccountRepository.save(mapToBankAccount(bankAccountData, companyDetails));
  }
  
  private BankAccount getByIdOrElseThrow(Long id) {
    return bankAccountRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
  }
  // todo delete
//  private BankAccount mapToBankAccount(BankAccountCreateBindingDto bankAccountData, CompanyDetails companyDetails) {
//    return new BankAccount()
//        .setIban(InputFormating.format(bankAccountData.getIban()))
//        .setBic(InputFormating.format(bankAccountData.getBic()))
//        .setCurrency(InputFormating.format(bankAccountData.getCurrency()))
//        .setCompanyDetails(companyDetails);
//  }
  // todo delete
//  private BankAccountView mapToBankAccountView(BankAccount bankAccount) {
//    return new BankAccountView(bankAccount);
//  }
}
