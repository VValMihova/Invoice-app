package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.BankAccountCreateDto;
import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.binding.UserRegisterDto;
import bg.softuni.invoice_app.model.dto.binding.BankAccountDto;
import bg.softuni.invoice_app.model.entity.User;

import java.util.List;

public interface UserService {
  void register(UserRegisterDto registerData);
  
  User getUserByEmail(String email);
  
  void updateCompany(CompanyDetailsDto companyData);
  
  void addBankAccount(BankAccountCreateDto bankAccountData);
  List<BankAccountDto> getAllBankAccounts();
  
  User getUserByCompanyEik(String eik);
  
  User getUserByCompanyVat(String vat);
}
