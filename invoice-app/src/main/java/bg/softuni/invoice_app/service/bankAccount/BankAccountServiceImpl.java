package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.service.user.UserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final UserService userService;
  private final RestClient restClient;
  
  public BankAccountServiceImpl(
      UserService userService, RestClient restClient) {
    this.userService = userService;
    this.restClient = restClient;
  }
  
  @Override
  public List<BankAccountView> getUserAccounts(String uuid) {
    return restClient
        .get()
        .uri("http://localhost:8081/bank-accounts/user/{uuid}", uuid)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {
        });
  }
  
  // todo connect
  @Override
  public BankAccountView getViewById(Long id) {
    return restClient
        .get()
        .uri("http://localhost:8081/bank-accounts/{id}", id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(BankAccountView.class);
  }
  
//  @Override
//  public BankAccount getByIban(String iban) {
//    return this.bankAccountRepository.findByIban(iban)
//        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
//  }
//
//  @Override
//  public void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit) {
//    BankAccount bankAccount = getByIdOrElseThrow(id);
//
//    bankAccount.setIban(InputFormating.format(bankAccountDataEdit.getIban()))
//        .setBic(InputFormating.format(bankAccountDataEdit.getBic()))
//        .setCurrency(InputFormating.format(bankAccountDataEdit.getCurrency()));
//
//    bankAccountRepository.save(bankAccount);
//  }
  
  
  @Override
  public void deleteBankAccount(Long id) {
    restClient
        .delete()
        .uri("http://localhost:8081/bank-accounts/{id}", id)
        .retrieve();
  }
  
  @Override
  public void addBankAccountUser(BankAccountCreateBindingDto bankAccountData, String uuid) {
    restClient
        .post()
        .uri("http://localhost:8081/bank-accounts/user/{uuid}", uuid)
        .body(bankAccountData)
        .retrieve();
  }
  @Override
  public BankAccountView updateBankAccount(Long id, BankAccountEditBindingDto bankAccountView) {
    return restClient.put()
        .uri("http://localhost:8081/bank-accounts/{id}", id)
        .body(bankAccountView)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(BankAccountView.class);
  }
  
//  private BankAccount getByIdOrElseThrow(Long id) {
//    return bankAccountRepository.findById(id)
//        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
//  }
}
