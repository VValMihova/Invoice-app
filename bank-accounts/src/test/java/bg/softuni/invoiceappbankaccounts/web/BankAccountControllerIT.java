package bg.softuni.invoiceappbankaccounts.web;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.entity.BankAccount;
import bg.softuni.invoiceappbankaccounts.repository.BankAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static bg.softuni.invoiceappbankaccounts.util.ErrorConstants.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerIT {
  @Autowired
  private BankAccountRepository bankAccountRepository;
  
  @Autowired
  private MockMvc mockMvc;
  
  @AfterEach
  public void clean() {
    this.bankAccountRepository.deleteAll();
  }
  
  @Test
  public void testDeleteBankAccount() throws Exception {
    BankAccount bankAccount = bankAccountRepository.save(new BankAccount()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID));
    
    mockMvc.perform(delete("/bank-accounts/{id}", bankAccount.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    
    Optional<BankAccount> deletedBankAccount = bankAccountRepository.findById(bankAccount.getId());
    assertTrue(deletedBankAccount.isEmpty());
  }
  
  @Test
  public void testDeleteBankAccount_notFound() throws Exception {
    mockMvc.perform(delete("/bank-accounts/{id}",NON_EXIST_ID )
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
  
  @Test
  public void testUpdateBankAccount() throws Exception {
    BankAccount existingBankAccount = new BankAccount()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID);
    bankAccountRepository.save(existingBankAccount);
    
    BankAccountEditBindingDto editDto = new BankAccountEditBindingDto()
        .setIban(TEST_IBAN_2)
        .setBic(TEST_BIC_2)
        .setCurrency(TEST_CURRENCY_2);
    
    mockMvc.perform(put("/bank-accounts/{id}", existingBankAccount.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(editDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.iban", is(TEST_IBAN_2)))
        .andExpect(jsonPath("$.bic", is(TEST_BIC_2)))
        .andExpect(jsonPath("$.currency", is(TEST_CURRENCY_2)));
  }
  @Test
  public void testUpdateBankAccount_ibanExists() throws Exception {
    BankAccount bankAccount1 = bankAccountRepository.save(new BankAccount()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID));
    
    BankAccount bankAccount2 = bankAccountRepository.save(new BankAccount()
        .setIban(TEST_IBAN_2)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY_2)
        .setUser(TEST_UUID));
    
    BankAccountEditBindingDto editDto = new BankAccountEditBindingDto();
    editDto.setIban(TEST_IBAN);
    editDto.setBic(TEST_BIC_2);
    editDto.setCurrency(TEST_CURRENCY_2);
    
    mockMvc.perform(put("/bank-accounts/{id}", bankAccount2.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(editDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("IBAN already exists"));
  }


  @Test
  public void testCreateBankAccount() throws Exception {
    BankAccountCreateBindingDto createDto = new BankAccountCreateBindingDto()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY);
    
    mockMvc.perform(post("/bank-accounts/user/{uuid}", TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(createDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.iban", is(TEST_IBAN)))
        .andExpect(jsonPath("$.bic", is(TEST_BIC)))
        .andExpect(jsonPath("$.currency", is(TEST_CURRENCY)));
  }
  
  @Test
  public void testCreateBankAccount_ibanExists() throws Exception {
    BankAccount existingBankAccount = new BankAccount()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID);
    bankAccountRepository.save(existingBankAccount);
    
    BankAccountCreateBindingDto createDto = new BankAccountCreateBindingDto()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY);
    
    mockMvc.perform(post("/bank-accounts/user/{uuid}", TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(createDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("IBAN already exists"));
  }
  @Test
  public void testGetUserAccounts() throws Exception {
    BankAccount bankAccount1 = new BankAccount()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID);
    BankAccount bankAccount2 = new BankAccount()
        .setIban(TEST_IBAN_2)
        .setBic(TEST_BIC_2)
        .setCurrency(TEST_CURRENCY_2)
        .setUser(TEST_UUID);
    
    bankAccountRepository.save(bankAccount1);
    bankAccountRepository.save(bankAccount2);
    
    mockMvc.perform(get("/bank-accounts/user/{uuid}", TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[?(@.iban == '" + TEST_IBAN + "')]").exists())
        .andExpect(jsonPath("$[?(@.iban == '" + TEST_IBAN_2 + "')]").exists())
        .andExpect(jsonPath("$[?(@.bic == '" + TEST_BIC + "')]").exists())
        .andExpect(jsonPath("$[?(@.bic == '" + TEST_BIC_2 + "')]").exists())
        .andExpect(jsonPath("$[?(@.currency == '" + TEST_CURRENCY + "')]").exists())
        .andExpect(jsonPath("$[?(@.currency == '" + TEST_CURRENCY_2 + "')]").exists());
  }
  
  @Test
  public void testGetUserAccounts_notFound() throws Exception {
    mockMvc.perform(get("/bank-accounts/user/{uuid}", "NON_EXISTENT_UUID")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }
  
  
  @Test
  public void testGetBankAccountById() throws Exception {
    BankAccount bankAccount = bankAccountRepository.save(new BankAccount()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID));
    
    mockMvc.perform(get("/bank-accounts/{id}", bankAccount.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(bankAccount.getId().intValue())))
        .andExpect(jsonPath("$.iban", is(bankAccount.getIban())))
        .andExpect(jsonPath("$.bic", is(bankAccount.getBic())))
        .andExpect(jsonPath("$.currency", is(bankAccount.getCurrency())));
  }
  
  @Test
  public void testGetBankAccountByIban() throws Exception {
    BankAccount bankAccount = bankAccountRepository.save(new BankAccount()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID));
    
    mockMvc.perform(get("/bank-accounts/iban/{iban}", bankAccount.getIban())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(bankAccount.getId().intValue())))
        .andExpect(jsonPath("$.iban", is(bankAccount.getIban())))
        .andExpect(jsonPath("$.bic", is(bankAccount.getBic())))
        .andExpect(jsonPath("$.currency", is(bankAccount.getCurrency())));
  }
  
  @Test
  public void testGetBankAccountByIban_notFound() throws Exception {
    mockMvc.perform(get("/bank-accounts/iban/{iban}", "NON_EXISTENT_IBAN")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
  
}
