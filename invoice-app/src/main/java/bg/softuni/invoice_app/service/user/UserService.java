package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.model.user.InvoiceAppUserDetails;

import java.util.Optional;

public interface UserService {
  void register(UserRegisterBindingDto registerData);
  
  User getUserByEmail(String email);
  
  void updateCompany(CompanyDetails companyDetails);
  
  User getUserByCompanyEik(String eik);
  
  User getUserByCompanyVat(String vat);
  
  CompanyDetailsView showCompanyDetails();
  
  CompanyDetails getCompanyDetails();
  
  Long getCurrentUserId();
  
  //  todo add exception
  User getUser();
  
  Optional<InvoiceAppUserDetails> getCurrentUser();
  String getUuid();
}
