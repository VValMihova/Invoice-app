package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountViewDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;

public interface BankAccountService {
  BankAccountViewDto getBankAccountById(Long id);
  
  void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit);
  
  void deleteBankAccount(Long id);
}
