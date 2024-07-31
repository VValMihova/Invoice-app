package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
  
  private final UserService userService;
  
  public AdminController(UserService userService) {
    this.userService = userService;
  }
  @Secured("ROLE_ADMIN")
  @GetMapping()
  public String adminPanel(Model model, @RequestParam(defaultValue = "0") int page) {
    Page<User> users = userService.findAllExceptCurrent(PageRequest.of(page, 10));
    model.addAttribute("users", users);
    return "admin";
  }
  
}
