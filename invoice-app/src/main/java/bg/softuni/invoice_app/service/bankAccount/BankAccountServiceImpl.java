package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Set;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final RestClient restClient;
  
  public BankAccountServiceImpl(
      RestClient restClient) {
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
  public BankAccountView getByIban(String iban) {
    return restClient
        .get()
        .uri("http://localhost:8081/bank-accounts/{iban}", iban)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(BankAccountView.class);
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
  public void editBankAccount(Long id, BankAccountEditBindingDto bankAccountData) {
    restClient
        .post()
        .uri("http://localhost:8081/bank-accounts")
        .body(bankAccountData)
        .retrieve();
  }
  
  
  @Override
  public void deleteBankAccount(Long id) {
    restClient
        .delete()
        .uri("http://localhost:8081/bank-accounts/{id}", id)
        .retrieve();
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
  // todo delete
//  private BankAccount getByIdOrElseThrow(Long id) {
//    return bankAccountRepository.findById(id)
//        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
//  }
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
