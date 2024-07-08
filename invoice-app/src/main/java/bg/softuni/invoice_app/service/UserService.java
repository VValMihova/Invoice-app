package bg.softuni.invoice_app.service;

import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.binding.UserRegisterDto;
import bg.softuni.invoice_app.model.entity.User;

public interface UserService {
  void register(UserRegisterDto registerData);
  
  User getUserByEmail(String email);
  
  void updateCompany(CompanyDetailsDto companyData);
}
