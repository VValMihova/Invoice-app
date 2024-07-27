package bg.softuni.invoice_app.service.bankAccountPersist;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.User;

import java.util.List;

public interface BankAccountPersistService {
  BankAccountPersist add(BankAccountView bankAccount, User user);
  
  List<BankAccountPersist> getAllPersistantAccounts();
  
  void delete(BankAccountPersist account);
}
