package bg.softuni.invoice_app.service.bankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.BankAccountRepository;
import bg.softuni.invoice_app.service.user.UserService;

import java.util.HashSet;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BankAccountServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BankAccountServiceImplTest {
  @MockBean
  private BankAccountRepository bankAccountRepository;
  
  @Autowired
  private BankAccountServiceImpl bankAccountServiceImpl;
  
  @MockBean
  private ModelMapper modelMapper;
  
  @MockBean
  private UserService userService;
  
  @Test
  void testGetById() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    
    BankAccount bankAccount = new BankAccount();
    bankAccount.setBic("Bic");
    bankAccount.setCompanyDetails(companyDetails);
    bankAccount.setCurrency("GBP");
    bankAccount.setIban("Iban");
    bankAccount.setId(1L);
    Optional<BankAccount> ofResult = Optional.of(bankAccount);
    when(bankAccountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    
    // Act
    BankAccountView actualById = bankAccountServiceImpl.getById(1L);
    
    // Assert
    verify(bankAccountRepository).findById(eq(1L));
    assertEquals("Bic", actualById.getBic());
    assertEquals("GBP", actualById.getCurrency());
    assertEquals("Iban", actualById.getIban());
    assertEquals(1L, actualById.getId().longValue());
  }
  
  @Test
  void testGetById2() {
    // Arrange
    Optional<BankAccount> emptyResult = Optional.empty();
    when(bankAccountRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> bankAccountServiceImpl.getById(1L));
    verify(bankAccountRepository).findById(eq(1L));
  }
  
  @Test
  void testGetById3() {
    // Arrange
    when(bankAccountRepository.findById(Mockito.<Long>any())).thenThrow(new NotFoundObjectException("Bank account"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> bankAccountServiceImpl.getById(1L));
    verify(bankAccountRepository).findById(eq(1L));
  }
  
  @Test
  void testGetByIban() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    
    BankAccount bankAccount = new BankAccount();
    bankAccount.setBic("Bic");
    bankAccount.setCompanyDetails(companyDetails);
    bankAccount.setCurrency("GBP");
    bankAccount.setIban("Iban");
    bankAccount.setId(1L);
    Optional<BankAccount> ofResult = Optional.of(bankAccount);
    when(bankAccountRepository.findByIban(Mockito.<String>any())).thenReturn(ofResult);
    
    // Act
    BankAccount actualByIban = bankAccountServiceImpl.getByIban("Iban");
    
    // Assert
    verify(bankAccountRepository).findByIban(eq("Iban"));
    assertSame(bankAccount, actualByIban);
  }
  
  @Test
  void testGetByIban2() {
    // Arrange
    Optional<BankAccount> emptyResult = Optional.empty();
    when(bankAccountRepository.findByIban(Mockito.<String>any())).thenReturn(emptyResult);
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> bankAccountServiceImpl.getByIban("Iban"));
    verify(bankAccountRepository).findByIban(eq("Iban"));
  }
  
  @Test
  void testGetByIban3() {
    // Arrange
    when(bankAccountRepository.findByIban(Mockito.<String>any()))
        .thenThrow(new NotFoundObjectException("Bank account"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> bankAccountServiceImpl.getByIban("Iban"));
    verify(bankAccountRepository).findByIban(eq("Iban"));
  }
  
  @Test
  void testFindAllForCompany() {
    // Arrange
    Optional<Set<BankAccount>> ofResult = Optional.of(new HashSet<>());
    when(bankAccountRepository.findByCompanyDetailsId(Mockito.<Long>any())).thenReturn(ofResult);
    
    // Act
    Set<BankAccountView> actualFindAllForCompanyResult = bankAccountServiceImpl.findAllForCompany(1L);
    
    // Assert
    verify(bankAccountRepository).findByCompanyDetailsId(eq(1L));
    assertTrue(actualFindAllForCompanyResult.isEmpty());
  }
  
  @Test
  void testFindAllForCompany2() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setId(1L);
    
    BankAccount bankAccount1 = new BankAccount();
    bankAccount1.setId(1L);
    bankAccount1.setCompanyDetails(companyDetails);
    
    BankAccount bankAccount2 = new BankAccount();
    bankAccount2.setId(2L);
    bankAccount2.setCompanyDetails(companyDetails);
    
    Set<BankAccount> bankAccountSet = new HashSet<>();
    bankAccountSet.add(bankAccount1);
    bankAccountSet.add(bankAccount2);
    
    when(bankAccountRepository.findByCompanyDetailsId(1L)).thenReturn(Optional.of(bankAccountSet));
    when(modelMapper.map(bankAccount1, BankAccountView.class)).thenReturn(new BankAccountView(bankAccount1));
    when(modelMapper.map(bankAccount2, BankAccountView.class)).thenReturn(new BankAccountView(bankAccount2));
    
    // Act
    Set<BankAccountView> result = bankAccountServiceImpl.findAllForCompany(1L);
    
    // Assert
    assertEquals(2, result.size());
    verify(bankAccountRepository, times(1)).findByCompanyDetailsId(1L);
    verify(modelMapper, times(2)).map(Mockito.any(BankAccount.class), eq(BankAccountView.class));
  }

  
  @Test
  void testFindAllForCompany3() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    
    BankAccount bankAccount = new BankAccount();
    bankAccount.setBic("Bic");
    bankAccount.setCompanyDetails(companyDetails);
    bankAccount.setCurrency("GBP");
    bankAccount.setIban("Iban");
    bankAccount.setId(1L);
    
    CompanyDetails companyDetails2 = new CompanyDetails();
    companyDetails2.setAddress("17 High St");
    companyDetails2.setCompanyName("42");
    companyDetails2.setEik("42");
    companyDetails2.setId(2L);
    companyDetails2.setManager("42");
    companyDetails2.setVatNumber("Vat Number");
    
    BankAccount bankAccount2 = new BankAccount();
    bankAccount2.setBic("42");
    bankAccount2.setCompanyDetails(companyDetails2);
    bankAccount2.setCurrency("USD");
    bankAccount2.setIban("42");
    bankAccount2.setId(2L);
    
    HashSet<BankAccount> bankAccountSet = new HashSet<>();
    bankAccountSet.add(bankAccount2);
    bankAccountSet.add(bankAccount);
    Optional<Set<BankAccount>> ofResult = Optional.of(bankAccountSet);
    when(bankAccountRepository.findByCompanyDetailsId(Mockito.<Long>any())).thenReturn(ofResult);
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BankAccountView>>any()))
        .thenReturn(new BankAccountView());
    
    // Act
    Set<BankAccountView> actualFindAllForCompanyResult = bankAccountServiceImpl.findAllForCompany(1L);
    
    // Assert
    verify(bankAccountRepository).findByCompanyDetailsId(eq(1L));
    verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), isA(Class.class));
    assertEquals(1, actualFindAllForCompanyResult.size());
  }
  
  @Test
  void testDeleteBankAccount() {
    // Arrange
    doNothing().when(bankAccountRepository).deleteById(Mockito.<Long>any());
    
    // Act
    bankAccountServiceImpl.deleteBankAccount(1L);
    
    // Assert that nothing has changed
    verify(bankAccountRepository).deleteById(eq(1L));
  }
  
  @Test
  void testDeleteBankAccount2() {
    // Arrange
    doThrow(new NotFoundObjectException("Object Type")).when(bankAccountRepository).deleteById(Mockito.<Long>any());
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> bankAccountServiceImpl.deleteBankAccount(1L));
    verify(bankAccountRepository).deleteById(eq(1L));
  }
  
  @Test
  void testAddBankAccount() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    
    BankAccount bankAccount = new BankAccount();
    bankAccount.setBic("Bic");
    bankAccount.setCompanyDetails(companyDetails);
    bankAccount.setCurrency("GBP");
    bankAccount.setIban("Iban");
    bankAccount.setId(1L);
    when(bankAccountRepository.save(Mockito.<BankAccount>any())).thenReturn(bankAccount);
    
    CompanyDetails companyDetails2 = new CompanyDetails();
    companyDetails2.setAddress("42 Main St");
    companyDetails2.setCompanyName("Company Name");
    companyDetails2.setEik("Eik");
    companyDetails2.setId(1L);
    companyDetails2.setManager("Manager");
    companyDetails2.setVatNumber("42");
    when(userService.getCompanyDetails()).thenReturn(companyDetails2);
    
    BankAccountCreateBindingDto bankAccountData = new BankAccountCreateBindingDto();
    bankAccountData.setBic("Bic");
    bankAccountData.setCurrency("GBP");
    bankAccountData.setIban("Iban");
    
    // Act
    bankAccountServiceImpl.addBankAccount(bankAccountData);
    
    // Assert
    verify(userService).getCompanyDetails();
    verify(bankAccountRepository).save(isA(BankAccount.class));
  }
  
  @Test
  void testAddBankAccount2() {
    // Arrange
    when(userService.getCompanyDetails()).thenThrow(new NotFoundObjectException("Object Type"));
    
    BankAccountCreateBindingDto bankAccountData = new BankAccountCreateBindingDto();
    bankAccountData.setBic("Bic");
    bankAccountData.setCurrency("GBP");
    bankAccountData.setIban("Iban");
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> bankAccountServiceImpl.addBankAccount(bankAccountData));
    verify(userService).getCompanyDetails();
  }
  
  @Test
  void testAddBankAccount3() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    when(userService.getCompanyDetails()).thenReturn(companyDetails);
    
    BankAccountCreateBindingDto bankAccountCreateBindingDto = new BankAccountCreateBindingDto();
    bankAccountCreateBindingDto.setBic("Bic");
    bankAccountCreateBindingDto.setCurrency("GBP");
    bankAccountCreateBindingDto.setIban("Iban");
    
    BankAccountCreateBindingDto bankAccountCreateBindingDto2 = new BankAccountCreateBindingDto();
    bankAccountCreateBindingDto2.setBic("Bic");
    bankAccountCreateBindingDto2.setCurrency("GBP");
    bankAccountCreateBindingDto2.setIban("Iban");
    
    BankAccountCreateBindingDto bankAccountCreateBindingDto3 = new BankAccountCreateBindingDto();
    bankAccountCreateBindingDto3.setBic("Bic");
    bankAccountCreateBindingDto3.setCurrency("GBP");
    bankAccountCreateBindingDto3.setIban("Iban");
    BankAccountCreateBindingDto bankAccountData = mock(BankAccountCreateBindingDto.class);
    when(bankAccountData.getCurrency()).thenThrow(new NotFoundObjectException("Object Type"));
    when(bankAccountData.getBic()).thenReturn("Bic");
    when(bankAccountData.setBic(Mockito.<String>any())).thenReturn(bankAccountCreateBindingDto);
    when(bankAccountData.setCurrency(Mockito.<String>any())).thenReturn(bankAccountCreateBindingDto2);
    when(bankAccountData.setIban(Mockito.<String>any())).thenReturn(bankAccountCreateBindingDto3);
    when(bankAccountData.getIban()).thenReturn("Iban");
    bankAccountData.setBic("Bic");
    bankAccountData.setCurrency("GBP");
    bankAccountData.setIban("Iban");
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> bankAccountServiceImpl.addBankAccount(bankAccountData));
    verify(bankAccountData).getBic();
    verify(bankAccountData).getCurrency();
    verify(bankAccountData).getIban();
    verify(bankAccountData).setBic(eq("Bic"));
    verify(bankAccountData).setCurrency(eq("GBP"));
    verify(bankAccountData).setIban(eq("Iban"));
    verify(userService).getCompanyDetails();
  }
}
