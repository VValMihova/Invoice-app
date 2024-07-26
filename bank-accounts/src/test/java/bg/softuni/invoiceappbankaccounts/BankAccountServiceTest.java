package bg.softuni.invoiceappbankaccounts;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class BankAccountServiceTest {
  
  @Autowired
  private BankAccountService bankAccountService;
  
  @Test
  public void testRestClient() {
    String uuid = "91071533-ae2f-4396-8a46-a7cc3e3d86e0"; // използвайте действителен UUID
    Set<BankAccountView> bankAccounts = bankAccountService.findAllForCompany(uuid);
    System.out.println(bankAccounts);
    
    // Можете също да добавите асерции за проверка на резултатите
    // Assertions.assertNotNull(bankAccounts);
    // Assertions.assertFalse(bankAccounts.isEmpty());
  }
}