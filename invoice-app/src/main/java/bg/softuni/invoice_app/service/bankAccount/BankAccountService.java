package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;

import java.util.Set;

public interface BankAccountService {
  BankAccountView getViewById(Long id);

  BankAccountView getByIban(String iban);

  Set<BankAccountView> findAllForCompany(String uuid);

  void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit);

  void deleteBankAccount(Long id);

  void addBankAccount(BankAccountCreateBindingDto bankAccountData);
}
