package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
  void register(UserRegisterBindingDto registerData);
  
  User getUserByEmail(String email);
  
  void updateCompany(CompanyDetails companyDetails);
  
  User getUserByCompanyEik(String eik);
  
  User getUserByCompanyVat(String vat);
  
  CompanyDetailsView showCompanyDetails();
  
  CompanyDetails getCompanyDetails();
  
  Long getCurrentUserId();
  
  User getUser();
  
  Page<User> findAllExceptCurrent(PageRequest of, String companyName, String eik);
  
  User findById(Long userId);
}
