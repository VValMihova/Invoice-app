package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.user.UserHelperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
public class ClientsController {
  private final RecipientDetailsService recipientDetailsService;
  private final UserHelperService userHelperService;
  
  public ClientsController(RecipientDetailsService recipientDetailsService, UserHelperService userHelperService) {
    this.recipientDetailsService = recipientDetailsService;
    this.userHelperService = userHelperService;
  }
  
  @GetMapping
  public String clientsPage(Model model) {
    model.addAttribute("clients", recipientDetailsService.findAll(userHelperService.getUser().getId()));
    return "clients";
  }
  
}
