package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountViewDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;

import java.util.List;

public interface UserService {
  void register(UserRegisterBindingDto registerData);
  
  User getUserByEmail(String email);
  
  void updateCompany(CompanyDetails companyDetails);
  
  void addBankAccount(BankAccountCreateBindingDto bankAccountData);
  List<BankAccountViewDto> getAllBankAccounts();
  
  User getUserByCompanyEik(String eik);
  
  User getUserByCompanyVat(String vat);
}
