package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.entity.BankAccount;

public interface BankAccountService {
  BankAccountView getById(Long id);
  
  BankAccount getByIban(String iban);
  
  void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit);
  
  void deleteBankAccount(Long id);
}
