package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.binding.UserRegisterDto;
import bg.softuni.invoice_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
      @Valid UserRegisterDto registerData,
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
  public String login() {
    return "login";
  }
//  MODEL ATTRIBUTES
  @ModelAttribute("registerInfo")
  public UserRegisterDto userRegisterDto(){
    return new UserRegisterDto();
  }
  
}
