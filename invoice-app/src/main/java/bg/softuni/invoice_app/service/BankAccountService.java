package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.view.BankAccountViewDto;
import bg.softuni.invoice_app.model.dto.binding.BankAccountEditBindingDto;

public interface BankAccountService {
  BankAccountViewDto getBankAccountById(Long id);
  
  void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit);
  
  void deleteBankAccount(Long id);
}
