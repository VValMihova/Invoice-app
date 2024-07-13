package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.user.UserHelperService;
import bg.softuni.invoice_app.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
  private final UserHelperService userHelperService;
  
  public HomeController(UserHelperService userHelperService) {
    this.userHelperService = userHelperService;
  }
  
  @GetMapping("/")
  public String getHome(Principal principal, Model model) {
    if (principal != null) {
      model.addAttribute("companyName", userHelperService.getUser().getCompanyDetails().getCompanyName());
    }
    return "index";
  }
}
