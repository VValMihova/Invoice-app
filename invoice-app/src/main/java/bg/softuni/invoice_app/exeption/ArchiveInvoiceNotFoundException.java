package bg.softuni.invoice_app.exeption;

public class ArchiveInvoiceNotFoundException extends RuntimeException {
  public ArchiveInvoiceNotFoundException(String message) {
    super(message);
  }
}