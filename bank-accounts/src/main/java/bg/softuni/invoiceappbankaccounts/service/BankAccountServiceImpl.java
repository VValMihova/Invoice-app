package bg.softuni.invoiceappbankaccounts.service;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.model.entity.BankAccount;
import bg.softuni.invoiceappbankaccounts.repository.BankAccountRepository;
import bg.softuni.invoiceappbankaccounts.service.exception.ObjectNotFoundException;
import bg.softuni.invoiceappbankaccounts.utils.InputFormating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  
  private final BankAccountRepository bankAccountRepository;
  
  public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
    this.bankAccountRepository = bankAccountRepository;
  }
  
  @Override
  public boolean addBankAccount(BankAccountCreateBindingDto bankAccountData) {
    //  todo connect
    //  BankAccount bankAccount = this.bankAccountRepository.save(mapToBankAccount(bankAccountData));
    return true;
  }
  
  //  todo make it for current user only
  @Override
  public Set<BankAccountView> findAllForCompany() {
//    Set<BankAccount> bankAccounts = this.bankAccountRepository.findByCompanyDetailsId(companyId)
//        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
//    if (bankAccounts.isEmpty()) {
//      return new HashSet<>();
//    } else {
//      return bankAccounts.stream()
//          .map(bankAccount -> this.modelMapper.map(bankAccount, BankAccountView.class))
//          .collect(Collectors.toSet());
//    }
    return this.bankAccountRepository.findAll()
        .stream()
        .map(BankAccountView::new)
        .collect(Collectors.toSet());
  }
  
  @Override
  public void deleteBankAccount(Long id) {
    BankAccount bankAccount = getByIdOrElseThrow(id);
    this.bankAccountRepository.deleteById(id);
  }
  
  @Override
  public BankAccountView findById(Long id) {
    return new BankAccountView(getByIdOrElseThrow(id));
  }
  
  @Override
  public List<BankAccountView> findByUuid(String uuid) {
    return bankAccountRepository.findAll()
        .stream()
        .filter(ba -> ba.getUser().equals(uuid))
        .map(BankAccountView::new)
        .toList();
  }
  
  @Override
  public BankAccountView addBankAccountUser(BankAccountCreateBindingDto bankAccountCreate, String uuid) {
    return new BankAccountView(bankAccountRepository.save(mapToBankAccount(bankAccountCreate, uuid)));
  }
  
  @Override
  public BankAccountView updateBankAccount(Long id, BankAccountEditBindingDto bankAccountEditBindingDto) {
    BankAccount bankAccount = getByIdOrElseThrow(id);
    // Актуализирайте полетата
    bankAccount.setIban(bankAccountEditBindingDto.getIban());
    bankAccount.setBic(bankAccountEditBindingDto.getBic());
    bankAccount.setCurrency(bankAccountEditBindingDto.getCurrency());
//    bankAccount.setUserUuid(bankAccountView.getUserUuid());
    
    bankAccountRepository.save(bankAccount);
    
    return new BankAccountView(bankAccount);
  }
  
  private BankAccount mapToBankAccount(
      BankAccountCreateBindingDto bankAccountData,
      String uuid) {
    return new BankAccount()
        .setIban(InputFormating.format(bankAccountData.getIban()))
        .setBic(InputFormating.format(bankAccountData.getBic()))
        .setCurrency(InputFormating.format(bankAccountData.getCurrency()))
        .setUser(uuid);
  }
  
  private BankAccount getByIdOrElseThrow(Long id) {
    return bankAccountRepository.findById(id)
        .orElseThrow(ObjectNotFoundException::new);
    // todo add exception here
    // .orElseThrow(() -> new NotFoundObjectException("Bank account"));
  }
}
