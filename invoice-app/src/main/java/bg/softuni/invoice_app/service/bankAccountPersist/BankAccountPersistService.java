package bg.softuni.invoice_app.service.bankAccountPersist;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.User;

public interface BankAccountPersistService {
  BankAccountPersist add(BankAccountView bankAccount, User user);
}
