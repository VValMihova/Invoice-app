package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.BankAccountCreateDto;
import bg.softuni.invoice_app.model.dto.binding.invoice.BankAccountDto;

public interface BankAccountService {
  BankAccountDto getBankAccountById(Long id);
  
  void editBankAccount(Long id, BankAccountCreateDto bankAccountCreateDto);
  
  void deleteBankAccount(Long id);
}
