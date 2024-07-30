package bg.softuni.invoice_app.service.bankAccountPersist;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.BankAccountPersistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static bg.softuni.invoice_app.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountPersistServiceImplTest {
  @Mock
  private BankAccountPersistRepository mockRepository;
  
  @Mock
  private ModelMapper mockModelMapper;
  
  @InjectMocks
  private BankAccountPersistServiceImpl toTest;
  
  @Test
  void testDelete() {
    BankAccountPersist account = new BankAccountPersist()
        .setIban(IBAN_1)
        .setBic(BIC_CODE)
        .setCurrency(CURRENCY_1);
    
    toTest.delete(account);
    
    verify(mockRepository).delete(account);
  }
  
  @Test
  void testGetAllPersistantAccounts() {
    BankAccountPersist account1 = new BankAccountPersist();
    BankAccountPersist account2 = new BankAccountPersist();
    List<BankAccountPersist> accounts = List.of(account1, account2);
    
    when(mockRepository.findAll()).thenReturn(accounts);
    
    List<BankAccountPersist> result = toTest.getAllPersistantAccounts();
    
    Assertions.assertEquals(accounts.size(), result.size());
  }
  
  @Test
  void testAdd_BankAccountDoesNotExist() {
    BankAccountView bankAccountView = new BankAccountView();
    bankAccountView.setIban(IBAN_1);
    bankAccountView.setBic(BIC_CODE);
    bankAccountView.setCurrency(CURRENCY_1);
    
    User user = new User();
    user.setId(TEST_ID);
    
    BankAccountPersist mappedBankAccount = new BankAccountPersist();
    mappedBankAccount.setIban(bankAccountView.getIban())
        .setBic(bankAccountView.getBic())
        .setCurrency(bankAccountView.getCurrency())
        .setUser(user);
    
    when(mockRepository.existsByIban(bankAccountView.getIban())).thenReturn(false);
    when(mockModelMapper.map(bankAccountView, BankAccountPersist.class)).thenReturn(mappedBankAccount);
    when(mockRepository.saveAndFlush(mappedBankAccount)).thenReturn(mappedBankAccount);
    when(mockRepository.getByIban(bankAccountView.getIban())).thenReturn(mappedBankAccount);
    
    BankAccountPersist result = toTest.add(bankAccountView, user);
    
    verify(mockRepository).existsByIban(bankAccountView.getIban());
    verify(mockModelMapper).map(bankAccountView, BankAccountPersist.class);
    verify(mockRepository).saveAndFlush(mappedBankAccount);
    verify(mockRepository).getByIban(bankAccountView.getIban());
    
    assertEquals(bankAccountView.getIban(), result.getIban());
    assertEquals(bankAccountView.getBic(), result.getBic());
    assertEquals(bankAccountView.getCurrency(), result.getCurrency());
    assertEquals(user, result.getUser());
  }
  
  @Test
  void testAdd_BankAccountExists() {
    BankAccountView bankAccountView = new BankAccountView();
    bankAccountView.setIban(IBAN_1);
    bankAccountView.setBic(BIC_CODE);
    bankAccountView.setCurrency(CURRENCY_1);
    
    User user = new User();
    user.setId(TEST_ID);
    
    BankAccountPersist existingBankAccount = new BankAccountPersist();
    existingBankAccount.setIban(bankAccountView.getIban())
        .setBic(bankAccountView.getBic())
        .setCurrency(bankAccountView.getCurrency())
        .setUser(user);
    
    when(mockRepository.existsByIban(bankAccountView.getIban())).thenReturn(true);
    when(mockRepository.getByIban(bankAccountView.getIban())).thenReturn(existingBankAccount);
    
    BankAccountPersist result = toTest.add(bankAccountView, user);
    
    verify(mockRepository).existsByIban(bankAccountView.getIban());
    verify(mockRepository).getByIban(bankAccountView.getIban());
    
    verify(mockModelMapper, never()).map(any(BankAccountView.class), eq(BankAccountPersist.class));
    verify(mockRepository, never()).saveAndFlush(any(BankAccountPersist.class));
    
    assertEquals(existingBankAccount, result);
    assertEquals(bankAccountView.getIban(), result.getIban());
    assertEquals(bankAccountView.getBic(), result.getBic());
    assertEquals(bankAccountView.getCurrency(), result.getCurrency());
  }
}
