package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.BankAccountCreateDto;
import bg.softuni.invoice_app.model.dto.binding.BankAccountDto;
import bg.softuni.invoice_app.model.dto.binding.BankAccountEditDto;

public interface BankAccountService {
  BankAccountDto getBankAccountById(Long id);
  
  void editBankAccount(Long id, BankAccountEditDto bankAccountDataEdit);
  
  void deleteBankAccount(Long id);
}
