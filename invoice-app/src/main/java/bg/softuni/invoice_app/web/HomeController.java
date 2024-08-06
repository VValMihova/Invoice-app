package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  private final UserService userService;
  
  public HomeController(UserService userService) {
    this.userService = userService;
  }
  
  @GetMapping("/")
  public String getHome(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails != null) {
      model.addAttribute("companyName", userService.getUserByEmail(userDetails.getUsername()).getCompanyDetails().getCompanyName());
      model.addAttribute("roles", userDetails.getAuthorities());
    }
    return "index";
  }
}
