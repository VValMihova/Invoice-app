package bg.softuni.invoice_app.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundObjectException extends RuntimeException {
  
  private final String objectType;
  
  public String getObjectType() {
    return objectType;
  }
  
  public NotFoundObjectException(String objectType) {
    this.objectType = objectType;
  }
  
}
