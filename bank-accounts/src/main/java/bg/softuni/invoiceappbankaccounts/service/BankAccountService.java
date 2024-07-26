package bg.softuni.invoiceappbankaccounts.service;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;

import java.util.List;
import java.util.Set;

public interface BankAccountService {
  void deleteBankAccount(Long id);
  
  BankAccountView findById(Long id);
  
  List<BankAccountView> findByUuid(String uuid);
  
  BankAccountView addBankAccountUser(BankAccountCreateBindingDto bankAccountCreate, String uuid);
  
  BankAccountView updateBankAccount(Long id, BankAccountEditBindingDto bankAccountEditBindingDto);
}
