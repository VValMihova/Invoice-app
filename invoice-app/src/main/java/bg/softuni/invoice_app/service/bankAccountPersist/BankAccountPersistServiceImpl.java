package bg.softuni.invoice_app.service.bankAccountPersist;

import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.BankAccountPersist;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.BankAccountPersistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BankAccountPersistServiceImpl implements BankAccountPersistService {
  private final BankAccountPersistRepository bankAccountPersistRepository;
  private final ModelMapper modelMapper;
  
  public BankAccountPersistServiceImpl(BankAccountPersistRepository bankAccountPersistRepository, ModelMapper modelMapper) {
    this.bankAccountPersistRepository = bankAccountPersistRepository;
    this.modelMapper = modelMapper;
  }
  
  @Override
  public BankAccountPersist add(BankAccount bankAccount, User user) {
    if (!bankAccountPersistRepository.existsByIban(bankAccount.getIban())) {
      bankAccountPersistRepository.saveAndFlush(modelMapper.map(bankAccount, BankAccountPersist.class)
          .setUser(user));
    }
    return bankAccountPersistRepository.getByIban(bankAccount.getIban());
  }
}
