package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.entity.BankAccount;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.service.user.UserHelperService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {
  private final UserService userService;
  private final BankAccountService bankAccountService;
  private final UserHelperService userHelperService;
  private final CompanyDetailsService companyDetailsService;
  
  public ProfileController(
      UserService userService,
      BankAccountService bankAccountService,
      UserHelperService userHelperService,
      CompanyDetailsService companyDetailsService) {
    this.userService = userService;
    this.bankAccountService = bankAccountService;
    this.userHelperService = userHelperService;
    this.companyDetailsService = companyDetailsService;
  }
  @GetMapping
  public ModelAndView profile() {
    ModelAndView modelAndView = new ModelAndView("profile");
    CompanyDetailsView companyDetails = userHelperService.getCompanyDetails();
    modelAndView.addObject("companyDetails", companyDetails);
    modelAndView.addObject("bankAccounts", this.bankAccountService.findAllForCompany(companyDetails.getId()));
    return modelAndView;
  }
  
  @GetMapping("/edit-company")
  public ModelAndView editCompany() {
    ModelAndView modelAndView = new ModelAndView("edit-company");
    modelAndView.addObject("companyDetails", userHelperService.getCompanyDetails());
    
    return modelAndView;
  }
  
  @PostMapping("/update-company")
  public String updateCompany(
      @Valid CompanyDetailsEditBindingDto companyData,
      BindingResult bindingResult,
      Model model) {
    
    if (bindingResult.hasErrors()) {
      model.addAttribute("companyDetails", companyData);
      model.addAttribute("org.springframework.validation.BindingResult.companyDetails", bindingResult);
      return "edit-company";
    }
    CompanyDetails updated = companyDetailsService.update(userHelperService.getCompanyDetails().getId(), companyData);
    userService.updateCompany(updated);
    
    return "redirect:/profile";
  }
  
  @GetMapping("/add-bank-account")
  public String showAddBankAccountForm() {
    return "add-bank-account";
  }
  
  @PostMapping("/add-bank-account")
  public String addBankAccount(
      @Valid BankAccountCreateBindingDto bankAccountData,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("bankAccountData", bankAccountData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bankAccountData", bindingResult);
      return "redirect:/profile/add-bank-account";
    }
    bankAccountService.addBankAccount(bankAccountData);

    // redirectAttributes.addFlashAttribute("successMessage", "Bank account added successfully!");
    return "redirect:/profile";

  }
  
  @GetMapping("/edit-bank-account/{id}")
  public String showEditBankAccountForm(@PathVariable Long id, Model model) {
    model.addAttribute("bankAccount", this.bankAccountService.getById(id));
    return "edit-bank-account";
  }
  
  @PostMapping("/edit-bank-account/{id}")
  public String editBankAccount(
      @PathVariable Long id,
      @Valid BankAccountEditBindingDto bankAccountDataEdit,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("bankAccount", bankAccountDataEdit);
      model.addAttribute("org.springframework.validation.BindingResult.bankAccount", bindingResult);
      return "edit-bank-account";
    }
    
    this.bankAccountService.editBankAccount(id, bankAccountDataEdit);
    return "redirect:/profile";
  }
  
  @PostMapping("/delete-bank-account/{id}")
  public String deleteBankAccount(@PathVariable Long id) {
    
    this.bankAccountService.deleteBankAccount(id);
    return "redirect:/profile";
  }
  
  //  MODEL ATTRIBUTES
  @ModelAttribute()
  public CompanyDetailsView companyData() {
    return userHelperService.getCompanyDetails();
  }
  
  @ModelAttribute("bankAccountData")
  public BankAccountCreateBindingDto bankAccountData() {
    return new BankAccountCreateBindingDto();
  }
  
  @ModelAttribute("bankAccountDataEdit")
  public BankAccountEditBindingDto bankAccountDataEdit() {
    return new BankAccountEditBindingDto();
  }
  
}
