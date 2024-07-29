package bg.softuni.invoiceappbankaccounts.web;

import bg.softuni.invoiceappbankaccounts.model.entity.BankAccount;
import bg.softuni.invoiceappbankaccounts.repository.BankAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerIT {
  private final static Long ID = 1L;
  private final static Long ID_2 = 2L;
  private final static String TEST_IBAN = "PL27114020040000300201355387";
  private final static String TEST_BIC = "BNPAFRPP";
  private final static String TEST_CURRENCY = "USD";
  private final static String TEST_IBAN_2 = "IT60X0542811101000000123456";
  private final static String TEST_BIC_2 = "DABADKKK";
  private final static String TEST_CURRENCY_2 = "EUR";
  private final static String TEST_UUID = "91071533-ae2f-4396-8a46-a7cc3e3d86e0";
  
  @Autowired
  private BankAccountRepository bankAccountRepository;
  
  @Autowired
  private MockMvc mockMvc;
  
  @AfterEach
  public void clean() {
    this.bankAccountRepository.deleteAll();
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
}
