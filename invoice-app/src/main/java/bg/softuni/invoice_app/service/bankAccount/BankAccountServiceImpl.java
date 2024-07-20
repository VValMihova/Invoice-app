package bg.softuni.invoice_app.service.bankAccount;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.BankAccountRepository;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.utils.InputFormating;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final BankAccountRepository bankAccountRepository;
  private final ModelMapper modelMapper;
  private final UserService userService;
  
  public BankAccountServiceImpl(
      BankAccountRepository bankAccountRepository,
      ModelMapper modelMapper,
      UserService userService) {
    this.bankAccountRepository = bankAccountRepository;
    this.modelMapper = modelMapper;
    this.userService = userService;
  }
  
  @Override
  public BankAccountView getViewById(Long id) {
    BankAccount bankAccount = getByIdOrElseThrow(id);
    
    return mapToBankAccountView(bankAccount);
  }
  
  @Override
  public BankAccount getByIban(String iban) {
    return this.bankAccountRepository.findByIban(iban)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
  }
  
  @Override
  public Set<BankAccountView> findAllForCompany(Long companyId) {
    Set<BankAccount> bankAccounts = this.bankAccountRepository.findByCompanyDetailsId(companyId)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
    if (bankAccounts.isEmpty()) {
      return new HashSet<>();
    } else {
      return bankAccounts.stream()
          .map(bankAccount -> this.modelMapper.map(bankAccount, BankAccountView.class))
          .collect(Collectors.toSet());
    }
  }
  
  @Override
  public void editBankAccount(Long id, BankAccountEditBindingDto bankAccountDataEdit) {
    BankAccount bankAccount = getByIdOrElseThrow(id);
    
    bankAccount.setIban(InputFormating.format(bankAccountDataEdit.getIban()))
        .setBic(InputFormating.format(bankAccountDataEdit.getBic()))
        .setCurrency(InputFormating.format(bankAccountDataEdit.getCurrency()));
    
    bankAccountRepository.save(bankAccount);
  }
  
  
  @Override
  public void deleteBankAccount(Long id) {
    BankAccount bankAccount = getByIdOrElseThrow(id);
    this.bankAccountRepository.deleteById(id);
  }
  
  @Override
  public void addBankAccount(BankAccountCreateBindingDto bankAccountData) {
    CompanyDetails companyDetails = userService.getCompanyDetails();
    
    this.bankAccountRepository.save(mapToBankAccount(bankAccountData, companyDetails));
  }
  
  private BankAccount getByIdOrElseThrow(Long id) {
    return bankAccountRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Bank account"));
  }
  
  private BankAccount mapToBankAccount(BankAccountCreateBindingDto bankAccountData, CompanyDetails companyDetails) {
    return new BankAccount()
        .setIban(InputFormating.format(bankAccountData.getIban()))
        .setBic(InputFormating.format(bankAccountData.getBic()))
        .setCurrency(InputFormating.format(bankAccountData.getCurrency()))
        .setCompanyDetails(companyDetails);
  }
  
  private BankAccountView mapToBankAccountView(BankAccount bankAccount) {
    return new BankAccountView(bankAccount);
  }
}
