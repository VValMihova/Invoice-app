package bg.softuni.invoiceappbankaccounts.service;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.model.entity.BankAccount;
import bg.softuni.invoiceappbankaccounts.repository.BankAccountRepository;
import bg.softuni.invoiceappbankaccounts.service.exception.ObjectNotFoundException;
import bg.softuni.invoiceappbankaccounts.utils.InputFormating;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  
  private final BankAccountRepository bankAccountRepository;
  
  public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
    this.bankAccountRepository = bankAccountRepository;
  }
  
  @Override
  public BankAccountView addBankAccount(BankAccountCreateBindingDto bankAccountData) {
    //  todo connect
//    CompanyDetails companyDetails = userService.getCompanyDetails();
//    this.bankAccountRepository.save(mapToBankAccount(bankAccountData, companyDetails));
    BankAccount bankAccount = this.bankAccountRepository.save(mapToBankAccount(bankAccountData));
    return new BankAccountView(bankAccount);
  }

//  @Override
//  public BankAccountView editBankAccount(BankAccountEditBindingDto bankAccountData) {
//    //  todo connect
//    BankAccount bankAccount = this.bankAccountRepository.save(mapToBankAccountEdit(bankAccountData));
//    return new BankAccountView(bankAccount);
  
  
  @Transactional
  @Override
  public BankAccountView editBankAccount(Long id, BankAccountEditBindingDto bankAccountEditBindingDto) {
    Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(id);
    if (!bankAccountOptional.isPresent()) {
      throw new RuntimeException("Bank account not found");
    }
    
    BankAccount bankAccount = bankAccountOptional.get();
    bankAccount.setIban(bankAccountEditBindingDto.getIban());
    bankAccount.setBic(bankAccountEditBindingDto.getBic());
    bankAccount.setCurrency(bankAccountEditBindingDto.getCurrency());
    
    BankAccount updatedAccount = bankAccountRepository.save(bankAccount);
    return convertToView(updatedAccount);
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
  public BankAccountView findByIban(String iban) {
    return bankAccountRepository.getByIban(iban);
  }
  
  private BankAccount mapToBankAccount(BankAccountCreateBindingDto bankAccountData) {//todo connect, CompanyDetails companyDetails) {
    return new BankAccount()
        .setIban(InputFormating.format(bankAccountData.getIban()))
        .setBic(InputFormating.format(bankAccountData.getBic()))
        .setCurrency(InputFormating.format(bankAccountData.getCurrency()));
    //  todo connect
    // .setCompanyDetails(companyDetails);
  }
  
  private BankAccount mapToBankAccountEdit(BankAccountEditBindingDto bankAccountData) {//todo connect, CompanyDetails companyDetails) {
    return new BankAccount()
        .setIban(InputFormating.format(bankAccountData.getIban()))
        .setBic(InputFormating.format(bankAccountData.getBic()))
        .setCurrency(InputFormating.format(bankAccountData.getCurrency()));
    //  todo connect
    // .setCompanyDetails(companyDetails);
  }
  
  private BankAccount getByIdOrElseThrow(Long id) {
    return bankAccountRepository.findById(id)
        .orElseThrow(ObjectNotFoundException::new);
    // todo add exception here
    // .orElseThrow(() -> new NotFoundObjectException("Bank account"));
  }
  private BankAccountView convertToView(BankAccount bankAccount) {
    BankAccountView view = new BankAccountView();
    view.setId(bankAccount.getId());
    view.setIban(bankAccount.getIban());
    view.setBic(bankAccount.getBic());
    view.setCurrency(bankAccount.getCurrency());
    return view;
  }
}