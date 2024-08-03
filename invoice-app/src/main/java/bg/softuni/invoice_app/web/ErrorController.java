package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.exeption.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@ControllerAdvice
public class ErrorController {

  private final MessageSource messageSource;
  
  public ErrorController(MessageSource messageSource) {
    this.messageSource = messageSource;
  }
  
  private String getLocalizedErrorMessage(String code, Object... args) {
    return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
  }
  
  @ExceptionHandler(RoleNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleRoleNotFound(RoleNotFoundException ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("role.not.found");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
  
  @ExceptionHandler(CompanyNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleCompanyNotFound(CompanyNotFoundException ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("company.not.found");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
  
  @ExceptionHandler(RecipientNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleRecipientNotFound(RecipientNotFoundException ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("recipient.not.found");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
  
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleUserNotFound(UserNotFoundException ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("user.not.found");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
  
  @ExceptionHandler(InvoiceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleInvoiceNotFound(InvoiceNotFoundException ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("invoice.not.found");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
  
  @ExceptionHandler(ArchiveInvoiceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleArchiveInvoiceNotFound(ArchiveInvoiceNotFoundException ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("archive.invoice.not.found");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
  
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleGlobalException(Exception ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("internal.server.error");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
  
  @ExceptionHandler(PdfGenerationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handlePdfGenerationException(PdfGenerationException ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("pdf.generation.failed");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
  @ExceptionHandler(DatabaseException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleDatabaseException(DatabaseException ex, Model model) {
    String errorMessage = getLocalizedErrorMessage("error.database");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
}
