package bg.softuni.invoiceappbankaccounts.service;

import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountCreateBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountEditBindingDto;
import bg.softuni.invoiceappbankaccounts.model.dto.BankAccountView;
import bg.softuni.invoiceappbankaccounts.model.entity.BankAccount;
import bg.softuni.invoiceappbankaccounts.repository.BankAccountRepository;
import bg.softuni.invoiceappbankaccounts.exception.ObjectNotFoundException;
import bg.softuni.invoiceappbankaccounts.utils.InputFormating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  
  private final BankAccountRepository bankAccountRepository;
  
  public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
    this.bankAccountRepository = bankAccountRepository;
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
  if (bankAccountRepository.existsByIban(bankAccountCreate.getIban())) {
    throw new IllegalArgumentException("IBAN already exists");
  }
  return new BankAccountView(bankAccountRepository.save(mapToBankAccount(bankAccountCreate, uuid)));
}
  
  @Override
  public BankAccountView updateBankAccount(Long id, BankAccountEditBindingDto bankAccountEditBindingDto) {
    BankAccount existingBankAccount = getByIdOrElseThrow(id);
    
    if (!existingBankAccount.getIban().equals(bankAccountEditBindingDto.getIban())) {
      if (bankAccountRepository.existsByIban(bankAccountEditBindingDto.getIban())) {
        throw new IllegalArgumentException("IBAN already exists");
      }
    }
    
    existingBankAccount.setIban(InputFormating.format(bankAccountEditBindingDto.getIban()));
    existingBankAccount.setBic(InputFormating.format(bankAccountEditBindingDto.getBic()));
    existingBankAccount.setCurrency(InputFormating.format(bankAccountEditBindingDto.getCurrency()));
    
    bankAccountRepository.save(existingBankAccount);
    
    return new BankAccountView(existingBankAccount);
  }
  
  @Override
  public BankAccountView getBankAccountByIban(String iban) {
    BankAccount bankAccount = bankAccountRepository.findByIban(iban)
        .orElseThrow(ObjectNotFoundException::new);
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
  }
}
