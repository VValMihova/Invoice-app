package bg.softuni.invoice_app.exeption;

public class InvoiceNotFoundException extends RuntimeException {
  public InvoiceNotFoundException(String message) {
    super(message);
  }
}