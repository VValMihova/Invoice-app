package bg.softuni.invoice_app.service.bankAccountPersist;

import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.User;

public interface BankAccountPersistService {
  BankAccountPersist add(BankAccount bankAccount, User user);
}
