package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

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
  public BankAccountView getViewByIban(String iban) {
    return restClient
        .get()
        .uri("http://localhost:8081/bank-accounts/iban/{iban}", iban)
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
  try {
    restClient
        .post()
        .uri("http://localhost:8081/bank-accounts/user/{uuid}", uuid)
        .body(bankAccountData)
        .retrieve()
        .body(BankAccountView.class);
  } catch (RestClientException ex) {
    throw new IllegalArgumentException("Failed to add bank account: " + ex.getMessage(), ex);
  }
}
  
  @Override
  public BankAccountView updateBankAccount(Long id, BankAccountEditBindingDto bankAccountView) {
    try {
      return restClient.put()
          .uri("http://localhost:8081/bank-accounts/{id}", id)
          .body(bankAccountView)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .body(BankAccountView.class);
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
        throw new IllegalArgumentException("Failed to update bank account: " + e.getResponseBodyAsString(), e);
      }
      throw new IllegalArgumentException("Failed to update bank account: " + e.getMessage(), e);
    }
  }
}
