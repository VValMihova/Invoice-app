package bg.softuni.invoice_app.exeption;

public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException(String message) {
    super(message);
  }
}