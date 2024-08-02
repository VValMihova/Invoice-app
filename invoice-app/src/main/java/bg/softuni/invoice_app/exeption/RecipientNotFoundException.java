package bg.softuni.invoice_app.exeption;

public class RecipientNotFoundException extends RuntimeException {
  public RecipientNotFoundException(String message) {
    super(message);
  }
}