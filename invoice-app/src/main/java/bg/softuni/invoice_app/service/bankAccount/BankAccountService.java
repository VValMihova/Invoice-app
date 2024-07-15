package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.entity.BankAccount;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.Set;

public interface BankAccountService {
  BankAccountView getById(Long id);
  
  BankAccount getByIban(String iban);
  
  Set<BankAccountView> findAllForCompany(Long companyId);
  
  void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit);
  
  void deleteBankAccount(Long id);
  
  void addBankAccount(BankAccountCreateBindingDto bankAccountData);
}
