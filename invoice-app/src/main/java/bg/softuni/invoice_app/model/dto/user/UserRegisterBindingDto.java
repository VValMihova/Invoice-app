package bg.softuni.invoice_app.model.dto.user;

import bg.softuni.invoice_app.validation.annotation.UniqueCompanyName;
import bg.softuni.invoice_app.validation.annotation.UniqueEik;
import bg.softuni.invoice_app.validation.annotation.UniqueEmail;
import bg.softuni.invoice_app.validation.annotation.UniqueVat;
import jakarta.validation.constraints.*;

public class UserRegisterBindingDto {
  @NotBlank(message = "{user.register.email.not.null}")
  @Email
  @UniqueEmail
  @Pattern(regexp = "([A-Za-z0-9]+[.\\-_]*?[A-Za-z0-9]+)@([A-Za-z0-9]+[.\\-_]*?[A-Za-z0-9]+).([A-Za-z0-9]+[.\\-_]*?[A-Za-z0-9]+)",
      message = "{user.register.email.valid}")
  private String email;
  
  @NotBlank
  @Size(min = 5, max = 15, message = "{user.register.password.size}")
  private String password;
  
  @NotBlank
  @Size(min = 5, max = 15, message = "{user.register.password.size}")
  private String confirmPassword;
  
  @NotBlank
  @UniqueCompanyName
  @Size(min = 2, max = 20, message = "{company.details.name.length}")
  private String companyName;
  
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.address.length}")
  private String address;
  
  @NotBlank
  @UniqueEik
  @Size(min = 10, max = 10, message = "{company.details.eik.length}")
  private String eik;
  
  @NotBlank
  @UniqueVat
  @Size(min = 12, max = 12, message = "{company.details.vat.length}")
  private String vatNumber;
  
  @NotBlank
  @Size(min = 2, max = 20, message = "{company.details.manager.length}")
  private String manager;
  
  
  public UserRegisterBindingDto() {
  }
  
  public String getEmail() {
    return email;
  }
  
  public UserRegisterBindingDto setEmail(String email) {
    this.email = email;
    return this;
  }
  
  public String getPassword() {
    return password;
  }
  
  public UserRegisterBindingDto setPassword(String password) {
    this.password = password;
    return this;
  }
  
  public String getConfirmPassword() {
    return confirmPassword;
  }
  
  public UserRegisterBindingDto setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    return this;
  }
  
  public String getCompanyName() {
    return companyName;
  }
  
  public UserRegisterBindingDto setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }
  
  public String getAddress() {
    return address;
  }
  
  public UserRegisterBindingDto setAddress(String address) {
    this.address = address;
    return this;
  }
  
  public String getEik() {
    return eik;
  }
  
  public UserRegisterBindingDto setEik(String eik) {
    this.eik = eik;
    return this;
  }
  
  public String getVatNumber() {
    return vatNumber;
  }
  
  public UserRegisterBindingDto setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }
  
  public String getManager() {
    return manager;
  }
  
  public UserRegisterBindingDto setManager(String manager) {
    this.manager = manager;
    return this;
  }
}
