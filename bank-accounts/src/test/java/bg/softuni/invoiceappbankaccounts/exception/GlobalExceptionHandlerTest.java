package bg.softuni.invoiceappbankaccounts.exception;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.service.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static bg.softuni.invoiceappbankaccounts.util.ErrorConstants.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private BankAccountService bankAccountService;
  
  @Test
  public void testUpdateBankAccount_UnhandledException() throws Exception {
    BankAccountEditBindingDto editDto = new BankAccountEditBindingDto()
        .setIban(TEST_IBAN_2)
        .setBic(TEST_BIC_2)
        .setCurrency(TEST_CURRENCY_2);
    
    doThrow(new RuntimeException("Unexpected error"))
        .when(bankAccountService).updateBankAccount(anyLong(), any(BankAccountEditBindingDto.class));
    
    mockMvc.perform(put("/bank-accounts/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(editDto)))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message", is("Unexpected error")))
        .andExpect(jsonPath("$.errors[0]", is("An unexpected error occurred")));
  }
}