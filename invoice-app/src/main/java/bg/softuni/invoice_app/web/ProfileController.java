package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.binding.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.binding.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.model.dto.view.BankAccountViewDto;
import bg.softuni.invoice_app.service.BankAccountService;
import bg.softuni.invoice_app.service.UserService;
import bg.softuni.invoice_app.service.impl.UserHelperService;
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
  
  public ProfileController(UserService userService, BankAccountService bankAccountService, UserHelperService userHelperService) {
    this.userService = userService;
    this.bankAccountService = bankAccountService;
    this.userHelperService = userHelperService;
  }
  
  @GetMapping
  public ModelAndView profile() {
    ModelAndView modelAndView = new ModelAndView("profile");
    modelAndView.addObject("companyDetails", userHelperService.getCompanyDetails());
    modelAndView.addObject("bankAccounts", userService.getAllBankAccounts());
    return modelAndView;
  }
  
  @GetMapping("/edit-company")
  public ModelAndView editCompany() {
    ModelAndView modelAndView = new ModelAndView("edit-company");
    modelAndView.addObject("companyDetails", userHelperService.getCompanyDetails());
    
    return modelAndView;
  }
  
  //  todo: must be with patch update for now works only with completely new data
  @PostMapping("/update-company")
  public String updateCompany(
      @Valid CompanyDetailsDto companyData,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("companyDetails", companyData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyDetails", bindingResult);
      return "redirect:/profile/edit-company";
    }
    userService.updateCompany(companyData);
    
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
    userService.addBankAccount(bankAccountData);
    
    // redirectAttributes.addFlashAttribute("successMessage", "Bank account added successfully!");
    return "redirect:/profile";
    
  }
  
  @GetMapping("/edit-bank-account/{id}")
  public String showEditBankAccountForm(@PathVariable Long id, Model model) {
    //BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
    
    BankAccountViewDto bankAccount = bankAccountService.getBankAccountById(id);
    model.addAttribute("bankAccount", bankAccount);
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
  
  @GetMapping("/delete-bank-account/{id}")
  public String deleteBankAccount(@PathVariable Long id) {
    bankAccountService.deleteBankAccount(id);
    return "redirect:/profile";
  }
  
  //  MODEL ATTRIBUTES
  @ModelAttribute()
  public CompanyDetailsDto companyData() {
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
