package bg.softuni.invoice_app.utils;

import org.springframework.stereotype.Component;

@Component
public class InputFormating {
  public static String format(String input) {
    return input.trim().toUpperCase();
  }
}
