package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
public class ClientsController {
  private final RecipientDetailsService recipientDetailsService;
  private final UserService userService;
  
  public ClientsController(
      RecipientDetailsService recipientDetailsService,
      UserService userService) {
    this.recipientDetailsService = recipientDetailsService;
    this.userService = userService;
  }
  
  @GetMapping
  public String clientsPage(Model model) {
    model.addAttribute("clients", recipientDetailsService.findAll(userService.getCurrentUserId()));
    return "clients";
  }
  
}
