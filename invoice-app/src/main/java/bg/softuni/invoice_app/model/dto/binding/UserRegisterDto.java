package bg.softuni.invoice_app.model.dto.binding;

import bg.softuni.invoice_app.validation.vallidation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegisterDto {
  @Email
  @UniqueEmail
  @NotBlank
  private String email;
  private String password;
  private String confirmPassword;
  private CompanyDetailsDto companyDetails;
  
  public UserRegisterDto() {
  }
  
  public String getEmail() {
    return email;
  }
  
  public UserRegisterDto setEmail(String email) {
    this.email = email;
    return this;
  }
  
  public String getPassword() {
    return password;
  }
  
  public UserRegisterDto setPassword(String password) {
    this.password = password;
    return this;
  }
  
  public String getConfirmPassword() {
    return confirmPassword;
  }
  
  public UserRegisterDto setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    return this;
  }
  
  public CompanyDetailsDto getCompanyDetails() {
    return companyDetails;
  }
  
  public UserRegisterDto setCompanyDetails(CompanyDetailsDto companyDetails) {
    this.companyDetails = companyDetails;
    return this;
  }
}
