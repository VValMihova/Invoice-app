package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.service.impl.UserHelperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {
  private final UserHelperService userHelperService;
  
  public ProfileController(UserHelperService userHelperService) {
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
}
