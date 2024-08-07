package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
  private final CompanyDetailsService companyDetailsService;
  private final MessageSource messageSource;
  
  public ProfileController(
      UserService userService,
      BankAccountService bankAccountService,
      CompanyDetailsService companyDetailsService, MessageSource messageSource) {
    this.userService = userService;
    this.bankAccountService = bankAccountService;
    this.companyDetailsService = companyDetailsService;
    this.messageSource = messageSource;
  }
  
  @GetMapping
  public ModelAndView profile(@AuthenticationPrincipal UserDetails userDetails) {
    ModelAndView modelAndView = new ModelAndView("user-profile");
    CompanyDetailsView companyDetails = userService.showCompanyDetails();
    modelAndView.addObject("companyDetails", companyDetails);
    modelAndView.addObject("bankAccounts",
        this.bankAccountService.getUserAccounts(userService.getUser().getUuid()));
    return modelAndView;
  }
  
  @GetMapping("/edit-company")
  public ModelAndView editCompany() {
    ModelAndView modelAndView = new ModelAndView("company-edit");
    modelAndView.addObject("companyDetails", userService.showCompanyDetails());
    
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
      return "company-edit";
    }
    CompanyDetails updated = companyDetailsService.update(userService.showCompanyDetails().getId(), companyData);
    userService.updateCompany(updated);
    
    return "redirect:/profile";
  }
  
  @GetMapping("/add-bank-account")
  public String showAddBankAccountForm() {
    return "bank-account-add";
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
    
    try {
      bankAccountService.addBankAccountUser(bankAccountData, userService.getUser().getUuid());
    } catch (IllegalArgumentException ex) {
      String errorMessage = ex.getMessage();
      if ("IBAN already exists".equals(errorMessage)) {
        errorMessage = messageSource.getMessage("iban.already.exists",
            null, LocaleContextHolder.getLocale());
      }
      bindingResult.rejectValue("iban", "error.bankAccountData", errorMessage);
      redirectAttributes.addFlashAttribute("bankAccountData", bankAccountData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bankAccountData", bindingResult);
      return "redirect:/profile/add-bank-account";
    }
    
    return "redirect:/profile";
  }
  
  
  @GetMapping("/edit-bank-account/{id}")
  public String showEditBankAccountForm(@PathVariable Long id, Model model) {
    model.addAttribute("bankAccountDataEdit", this.bankAccountService.getViewById(id));
    return "bank-account-edit";
  }
  
  @PostMapping("/edit-bank-account/{id}")
  public String editBankAccount(
      @PathVariable Long id,
      @Valid BankAccountEditBindingDto bankAccountDataEdit,
      BindingResult bindingResult,
      Model model) {
    
    if (bindingResult.hasErrors()) {
      model.addAttribute("bankAccountDataEdit", bankAccountDataEdit);
      model.addAttribute("org.springframework.validation.BindingResult.bankAccountDataEdit", bindingResult);
      return "bank-account-edit";
    }
    
    try {
      bankAccountService.updateBankAccount(id, bankAccountDataEdit);
    } catch (IllegalArgumentException ex) {
      String errorMessage = ex.getMessage();
      if ("IBAN already exists".equals(errorMessage)) {
        errorMessage = messageSource.getMessage("iban.already.exists", null,
            LocaleContextHolder.getLocale());
      }
      bindingResult.rejectValue("iban", "error.bankAccountDataEdit", errorMessage);
      model.addAttribute("bankAccountDataEdit", bankAccountDataEdit);
      model.addAttribute("org.springframework.validation.BindingResult.bankAccountDataEdit", bindingResult);
      return "bank-account-edit";
    }
    
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
    return userService.showCompanyDetails();
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
