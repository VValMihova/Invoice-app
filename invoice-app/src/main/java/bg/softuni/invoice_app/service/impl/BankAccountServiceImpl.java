package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.dto.binding.BankAccountCreateDto;
import bg.softuni.invoice_app.model.dto.binding.invoice.BankAccountDto;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.repository.BankAccountRepository;
import bg.softuni.invoice_app.service.BankAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final BankAccountRepository bankAccountRepository;
  private final UserHelperService userHelperService;
  private final ModelMapper modelMapper;
  
  public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserHelperService userHelperService, ModelMapper modelMapper) {
    this.bankAccountRepository = bankAccountRepository;
    this.userHelperService = userHelperService;
    this.modelMapper = modelMapper;
  }
  
  
  @Override
  public BankAccountDto getBankAccountById(Long id) {
    return modelMapper.map(this.bankAccountRepository.findById(id),BankAccountDto.class);
  }
  
  @Override
  public void editBankAccount(Long id, BankAccountCreateDto bankAccountCreateDto) {
    BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
    
    bankAccount.setIban(bankAccountCreateDto.getIban())
        .setBic(bankAccountCreateDto.getBic())
        .setCurrency(bankAccountCreateDto.getCurrency());
    
    bankAccountRepository.save(bankAccount);
  }
  
  @Override
  public void deleteBankAccount(Long id) {
    BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
    
    bankAccountRepository.delete(bankAccount);
    
  }
}
