package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final RestClient restClient;
  
  public BankAccountServiceImpl(
      RestClient restClient) {
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
  
  @Override
  public BankAccountView getViewById(Long id) {
    return restClient
        .get()
        .uri("http://localhost:8081/bank-accounts/{id}", id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(BankAccountView.class);
  }
  
  
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
}
