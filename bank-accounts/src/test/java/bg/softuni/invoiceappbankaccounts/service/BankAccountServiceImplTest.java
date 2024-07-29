package bg.softuni.invoiceappbankaccounts.service;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.model.entity.BankAccount;
import bg.softuni.invoiceappbankaccounts.repository.BankAccountRepository;
import bg.softuni.invoiceappbankaccounts.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {
  private final static Long ID = 1L;
  private final static Long ID_2 = 2L;
  private final static String TEST_IBAN = "IBAN123";
  private final static String TEST_BIC = "BIC123";
  private final static String TEST_CURRENCY = "USD";
  private final static String TEST_IBAN_2 = "IBAN456";
  private final static String TEST_BIC_2 = "BIC456";
  private final static String TEST_CURRENCY_2 = "EUR";
  private final static String TEST_UUID = "91071533-ae2f-4396-8a46-a7cc3e3d86e0";
  
  @Mock
  private BankAccountRepository bankAccountRepository;
  private BankAccountServiceImpl toTest;
  
  @BeforeEach
  void setUp() {
    this.toTest = new BankAccountServiceImpl(bankAccountRepository);
  }
  
  @Test
  void getBankAccountByIban_found() {
    String iban = TEST_IBAN;
    BankAccount bankAccount = new BankAccount()
        .setId(ID)
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID);
    
    when(bankAccountRepository.findByIban(iban)).thenReturn(Optional.of(bankAccount));
    
    BankAccountView result = toTest.getBankAccountByIban(iban);
    
    assertEquals(bankAccount.getId(), result.getId());
    assertEquals(bankAccount.getIban(), result.getIban());
    assertEquals(bankAccount.getBic(), result.getBic());
    assertEquals(bankAccount.getCurrency(), result.getCurrency());
    
    verify(bankAccountRepository).findByIban(iban);
  }
  @Test
  void getBankAccountByIban_notFound() {
    String iban = TEST_IBAN;
    
    when(bankAccountRepository.findByIban(iban)).thenReturn(Optional.empty());
    
    assertThrows(ObjectNotFoundException.class, () -> toTest.getBankAccountByIban(iban));
    
    verify(bankAccountRepository).findByIban(iban);
  }
  
  @Test
  void updateBankAccount_success() {
    BankAccountEditBindingDto editDto = new BankAccountEditBindingDto();
    editDto.setIban(TEST_IBAN_2);
    editDto.setBic(TEST_BIC_2);
    editDto.setCurrency(TEST_CURRENCY_2);
    
    BankAccount existingBankAccount = new BankAccount()
        .setId(ID)
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID);
    
    when(bankAccountRepository.findById(ID)).thenReturn(Optional.of(existingBankAccount));
    when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(existingBankAccount);
    
    BankAccountView result = toTest.updateBankAccount(ID, editDto);
    
    assertEquals(TEST_IBAN_2, result.getIban());
    assertEquals(TEST_BIC_2, result.getBic());
    assertEquals(TEST_CURRENCY_2, result.getCurrency());
    
    verify(bankAccountRepository).findById(ID);
    verify(bankAccountRepository).save(any(BankAccount.class));
  }
  @Test
  void updateBankAccount_notFound() {
    BankAccountEditBindingDto editDto = new BankAccountEditBindingDto();
    
    when(bankAccountRepository.findById(ID)).thenReturn(Optional.empty());
    
    assertThrows(ObjectNotFoundException.class, () -> toTest.updateBankAccount(ID, editDto));
    
    verify(bankAccountRepository).findById(ID);
    verify(bankAccountRepository, never()).save(any(BankAccount.class));
  }
  
  
  @Test
  void addBankAccountUser_success() {
    BankAccountCreateBindingDto createDto = new BankAccountCreateBindingDto();
    createDto.setIban(TEST_IBAN);
    createDto.setBic(TEST_BIC);
    createDto.setCurrency(TEST_CURRENCY);
    
    BankAccount bankAccount = new BankAccount()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID);
    
    when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
    
    BankAccountView result = toTest.addBankAccountUser(createDto, TEST_UUID);
    
    assertEquals(TEST_IBAN, result.getIban());
    assertEquals(TEST_BIC, result.getBic());
    assertEquals(TEST_CURRENCY, result.getCurrency());
    
    verify(bankAccountRepository).save(any(BankAccount.class));
  }
  
  @Test
  void addBankAccountUser_failure() {
    BankAccountCreateBindingDto createDto = new BankAccountCreateBindingDto()
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY);
    
    when(bankAccountRepository.save(any(BankAccount.class))).thenThrow(new RuntimeException("Error saving bank account"));
    
    assertThrows(RuntimeException.class, () -> toTest.addBankAccountUser(createDto, TEST_UUID));
    
    verify(bankAccountRepository).save(any(BankAccount.class));
  }
  
  
  @Test
  void findByUuid_found() {
    String uuid = TEST_UUID;
    BankAccount bankAccount1 = new BankAccount()
        .setId(ID)
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(uuid);
    
    
    BankAccount bankAccount2 = new BankAccount()
        .setId(ID_2)
        .setIban(TEST_IBAN_2)
        .setBic(TEST_BIC_2)
        .setCurrency(TEST_CURRENCY_2)
        .setUser(uuid);
    
    when(bankAccountRepository.findAll()).thenReturn(List.of(bankAccount1, bankAccount2));
    
    List<BankAccountView> result = toTest.findByUuid(uuid);
    
    assertEquals(2, result.size());
    assertEquals(bankAccount1.getId(), result.get(0).getId());
    assertEquals(bankAccount1.getIban(), result.get(0).getIban());
    assertEquals(bankAccount1.getBic(), result.get(0).getBic());
    assertEquals(bankAccount1.getCurrency(), result.get(0).getCurrency());
    
    assertEquals(bankAccount2.getId(), result.get(1).getId());
    assertEquals(bankAccount2.getIban(), result.get(1).getIban());
    assertEquals(bankAccount2.getBic(), result.get(1).getBic());
    assertEquals(bankAccount2.getCurrency(), result.get(1).getCurrency());
    
    verify(bankAccountRepository).findAll();
  }
  
  @Test
  void findByUuid_notFound() {
    when(bankAccountRepository.findAll()).thenReturn(List.of());
    
    List<BankAccountView> result = toTest.findByUuid(TEST_UUID);
    
    assertEquals(0, result.size());
    
    verify(bankAccountRepository).findAll();
  }
  
  @Test
  void findById_found() {
    Long id = ID;
    BankAccount bankAccount = new BankAccount()
        .setId(ID)
        .setIban(TEST_IBAN)
        .setBic(TEST_BIC)
        .setCurrency(TEST_CURRENCY)
        .setUser(TEST_UUID);
    
    when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));
    
    BankAccountView result = toTest.findById(id);
    
    assertEquals(bankAccount.getId(), result.getId());
    assertEquals(bankAccount.getIban(), result.getIban());
    assertEquals(bankAccount.getBic(), result.getBic());
    assertEquals(bankAccount.getCurrency(), result.getCurrency());
    
    verify(bankAccountRepository).findById(id);
  }
  
  @Test
  void findById_notFound() {
    Long id = ID;
    
    when(bankAccountRepository.findById(id)).thenReturn(Optional.empty());
    
    assertThrows(ObjectNotFoundException.class, () -> toTest.findById(id));
  }
  
  
  @Test
  void deleteBankAccount_found() {
    Long id = ID;
    BankAccount bankAccount = new BankAccount();
    bankAccount.setId(id);
    
    when(bankAccountRepository.findById(id)).thenReturn(java.util.Optional.of(bankAccount));
    
    toTest.deleteBankAccount(id);
    
    verify(bankAccountRepository).deleteById(id);
  }
  
  @Test
  void deleteBankAccount_notFound() {
    Long id = ID;
    
    when(bankAccountRepository.findById(id)).thenReturn(java.util.Optional.empty());
    
    assertThrows(ObjectNotFoundException.class, () -> toTest.deleteBankAccount(id));
  }
}
