package bg.softuni.invoiceappbankaccounts.service;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;

import java.util.Set;

public interface BankAccountService {
  BankAccountView addBankAccount(BankAccountCreateBindingDto bankAccountData);
  
//  todo make it for current user only
  Set<BankAccountView> findAllForCompany();
  
  void deleteBankAccount(Long id);
  
  BankAccountView findById(Long id);
}
