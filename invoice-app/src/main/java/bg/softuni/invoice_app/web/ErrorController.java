package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.exeption.PdfGenerationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@ControllerAdvice
public class ErrorController {
  
  @ExceptionHandler(NotFoundObjectException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView notFound(NotFoundObjectException objectNotFoundException) {
    ModelAndView modelAndView = new ModelAndView();
    if (Objects.equals(objectNotFoundException.getObjectType(), "Invoice")) {
      modelAndView.setViewName("invoice-not-found");
      modelAndView.addObject("objectType", objectNotFoundException.getObjectType());
    }
    if (Objects.equals(objectNotFoundException.getObjectType(), "Bank account")) {
      modelAndView.setViewName("bank-account-not-found");
      modelAndView.addObject("objectType", objectNotFoundException.getObjectType());
    }
    if (Objects.equals(objectNotFoundException.getObjectType(), "Recipient")) {
      modelAndView.setViewName("recipients-not-found");
      modelAndView.addObject("objectType", objectNotFoundException.getObjectType());
    }
    
    return modelAndView;
  }
  
  @ExceptionHandler(PdfGenerationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView pdfFailed(PdfGenerationException pdfGenerationException) {
    ModelAndView modelAndView = new ModelAndView();
    if (Objects.equals(pdfGenerationException.getObjectType(), "Pdf")) {
      modelAndView.setViewName("pdf-failed");
      modelAndView.addObject("objectType", pdfGenerationException.getObjectType());
    }
    return modelAndView;
  }
}
