package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.binding.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.dto.view.BankAccountViewDto;
import bg.softuni.invoice_app.model.entity.User;

import java.util.List;

public interface UserService {
  void register(UserRegisterBindingDto registerData);
  
  User getUserByEmail(String email);
  
  void updateCompany(CompanyDetailsDto companyData);
  
  void addBankAccount(BankAccountCreateBindingDto bankAccountData);
  List<BankAccountViewDto> getAllBankAccounts();
  
  User getUserByCompanyEik(String eik);
  
  User getUserByCompanyVat(String vat);
}
