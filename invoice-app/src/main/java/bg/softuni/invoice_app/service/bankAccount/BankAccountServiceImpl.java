package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.Invoice;
import bg.softuni.invoice_app.repository.BankAccountRepository;
import bg.softuni.invoice_app.service.user.UserHelperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final BankAccountRepository bankAccountRepository;
  
  public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
    this.bankAccountRepository = bankAccountRepository;
  }
  
  @Override
  public BankAccountView getById(Long id) {
    BankAccount bankAccount = this.bankAccountRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
    
    return mapToBankAccountView(bankAccount);
  }
  
  @Override
  public BankAccount getByIban(String iban) {
    return this.bankAccountRepository.findByIban(iban)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
  }
  
  @Override
  public void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit) {
    BankAccount bankAccount = bankAccountRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
    
    bankAccount.setIban(bankAccountDataEdit.getIban())
        .setBic(bankAccountDataEdit.getBic())
        .setCurrency(bankAccountDataEdit.getCurrency());
    
    bankAccountRepository.save(bankAccount);
  }
  
  @Override
  public void deleteBankAccount(Long id) {
    BankAccount bankAccount = bankAccountRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
    
    bankAccountRepository.delete(bankAccount);
  }
  private BankAccountView mapToBankAccountView(BankAccount bankAccount) {
    return new BankAccountView(bankAccount);
  }
}
