package bg.softuni.invoice_app.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class SecurityUtils {
  
  public static Principal getCurrentUser() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
