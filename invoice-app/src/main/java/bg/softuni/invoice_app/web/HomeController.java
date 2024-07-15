package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.service.user.UserHelperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  private final UserHelperService userHelperService;
  
  public HomeController(UserHelperService userHelperService) {
    this.userHelperService = userHelperService;
  }
  
  @GetMapping("/")
  public String getHome( Model model) {
     // model.addAttribute("companyName", userHelperService.getCompanyDetails().getCompanyName());
    return "index";
  }
}
