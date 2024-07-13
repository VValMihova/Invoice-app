package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.service.user.UserService;
import bg.softuni.invoice_app.exeption.DatabaseException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final MessageSource messageSource;
  
  public UserController(UserService userService,
                        @Qualifier("messageSource") MessageSource messageSource) {
    this.userService = userService;
    this.messageSource = messageSource;
  }
  
  @GetMapping("/register")
  public String register() {
    return "register";
  }
  
  @PostMapping("/register")
  public String doRegister(
      @Valid UserRegisterBindingDto registerData,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Locale locale) {
    
    if (registerData.getPassword() == null
        || !registerData.getPassword().equals(registerData.getConfirmPassword())) {
      String errorMessage = messageSource.getMessage("error.confirmPassword.mismatch", null, locale);
      bindingResult.addError(new FieldError("registerData", "confirmPassword", errorMessage));
    }
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("registerData", registerData);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
      return "redirect:/users/register";
    }
    
    try {
      this.userService.register(registerData);
    } catch (DatabaseException e) {
      String errorMessage = messageSource.getMessage("error.database", null, locale);
      redirectAttributes.addFlashAttribute("dbError", errorMessage);
      return "redirect:/users/register";
    }
    
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
  
  @ExceptionHandler(DatabaseException.class)
  public String handleDatabaseException(DatabaseException e, RedirectAttributes redirectAttributes, Locale locale) {
    String errorMessage = messageSource.getMessage("error.database", null, locale);
    redirectAttributes.addFlashAttribute("error", errorMessage);
    return "redirect:/users/register";
  }
}
