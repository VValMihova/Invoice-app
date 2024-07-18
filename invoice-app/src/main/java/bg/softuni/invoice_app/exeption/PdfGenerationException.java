package bg.softuni.invoice_app.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PdfGenerationException extends RuntimeException {
  
  private final String objectType;
  
  public String getObjectType() {
    return objectType;
  }
  
  public PdfGenerationException(String objectType) {
    this.objectType = objectType;
  }
  
}
