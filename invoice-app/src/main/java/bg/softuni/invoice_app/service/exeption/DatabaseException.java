package bg.softuni.invoice_app.service.exeption;

public class DatabaseException extends RuntimeException{
  public DatabaseException(String message) {
    super(message);
  }
}
