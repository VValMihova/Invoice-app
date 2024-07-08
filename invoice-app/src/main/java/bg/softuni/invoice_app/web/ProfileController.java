package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.binding.CompanyDetailsDto;
import bg.softuni.invoice_app.service.UserService;
import bg.softuni.invoice_app.service.impl.UserHelperService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {
  private final UserService userService;
  private final UserHelperService userHelperService;
  
  public ProfileController(UserService userService, UserHelperService userHelperService) {
    this.userService = userService;
    this.userHelperService = userHelperService;
  }
  
  @GetMapping
  public ModelAndView profile() {
    ModelAndView modelAndView = new ModelAndView("profile");
    modelAndView.addObject("companyDetails", userHelperService.getCompanyDetails());
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
      RedirectAttributes redirectAttributes){
    
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("companyDetails", companyData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyDetails", bindingResult);
      return "redirect:/profile/edit-company";
    }
    userService.updateCompany(companyData);
    
    return "redirect:/profile";
  }
  
//  MODEL ATTRIBUTES
  @ModelAttribute
  public CompanyDetailsDto companyData() {
    return userHelperService.getCompanyDetails();
  }
}
