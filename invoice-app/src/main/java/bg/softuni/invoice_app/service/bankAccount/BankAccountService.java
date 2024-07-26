package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;

import java.util.List;

public interface BankAccountService {
  List<BankAccountView> getUserAccounts(String uuid);
  
  BankAccountView getViewById(Long id);

//  BankAccount getByIban(String iban);

//  Set<BankAccountView> findAllForCompany(Long companyId);
  
  //void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit);
  
  void deleteBankAccount(Long id);

//  void addBankAccount(BankAccountCreateBindingDto bankAccountData);
  
  void addBankAccountUser(BankAccountCreateBindingDto bankAccountData, String uuid);
  
  BankAccountView updateBankAccount(Long id, BankAccountEditBindingDto bankAccountEditBindingDto);
}
