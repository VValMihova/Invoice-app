package bg.softuni.invoiceappbankaccounts.service;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;

import java.util.Set;

public interface BankAccountService {
Set<BankAccountView> getAllAccountsPerUser(String uuid);
  
  BankAccountView addBankAccount(BankAccountCreateBindingDto bankAccountData, String uuid);
  BankAccountView editBankAccount(Long id, BankAccountEditBindingDto bankAccountData);
  
//  todo make it for current user only
  Set<BankAccountView> findAllForCompany(String uuid);
  
  void deleteBankAccount(Long id);
  
  BankAccountView findById(Long id);
  BankAccountView findByIban(String iban);
}
