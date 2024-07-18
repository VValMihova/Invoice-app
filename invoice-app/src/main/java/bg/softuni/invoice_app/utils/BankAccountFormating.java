package bg.softuni.invoice_app.utils;

import org.springframework.stereotype.Component;

@Component
public class BankAccountFormating {
  public static String formatIban(String iban) {
    return iban.trim().toUpperCase();
  }
  
  public static String formatBic(String bic) {
    return bic.trim().toUpperCase();
  }
  
  public static String formatCurrency(String currency) {
    return currency.trim().toUpperCase();
  }
}
