package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  
  public UserController(UserService userService) {
    this.userService = userService;
  }
  
  @GetMapping("/register")
  public String register() {
    return "register";
  }
  
  @PostMapping("/register")
  public String doRegister(
      @Valid UserRegisterBindingDto registerData,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    
    if (registerData.getPassword() == null
        || !registerData.getPassword().equals(registerData.getConfirmPassword())) {
      bindingResult.addError(
          new FieldError(
              "differentConfirmPassword",
              "confirmPassword",
              "Passwords must be the same!"));
    }
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("registerData", registerData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
      return "redirect:/users/register";
    }
    
    this.userService.register(registerData);
    
    return "redirect:/users/login";
  }
  
  @GetMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error, Model model) {
    if (error != null) {
      model.addAttribute("loginError", "Invalid email or password! Please try again!");
    }
    return "login";
  }
  
  //  MODEL ATTRIBUTES
  @ModelAttribute("registerData")
  public UserRegisterBindingDto registerData() {
    return new UserRegisterBindingDto();
  }
  
}
