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
  
  @GetMapping("/role-not-found")
  public void throwRoleNotFoundException() {
    throw new RoleNotFoundException(ErrorMessages.ROLE_NOT_FOUND);
  }
  
  @GetMapping("/company-not-found")
  public void throwCompanyNotFoundException() {
    throw new CompanyNotFoundException(ErrorMessages.COMPANY_NOT_FOUND);
  }
  
  @GetMapping("/recipient-not-found")
  public void throwRecipientNotFoundException() {
    throw new RecipientNotFoundException(ErrorMessages.RECIPIENT_NOT_FOUND);
  }
  
  @GetMapping("/user-not-found")
  public void throwUserNotFoundException() {
    throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
  }
  
  @GetMapping("/invoice-not-found")
  public void throwInvoiceNotFoundException() {
    throw new InvoiceNotFoundException(ErrorMessages.INVOICE_NOT_FOUND);
  }
  
  @GetMapping("/archive-invoice-not-found")
  public void throwArchiveInvoiceNotFoundException() {
    throw new ArchiveInvoiceNotFoundException(ErrorMessages.ARCHIVE_INVOICE_NOT_FOUND);
  }
  
  @GetMapping("/pdf-generation-failed")
  public void throwPdfGenerationException() {
    throw new PdfGenerationException(ErrorMessages.PDF_GENERATION_FAILED);
  }
  
  @GetMapping("/internal-server-error")
  public void throwGlobalException() {
    throw new RuntimeException(ErrorMessages.INTERNAL_SERVER_ERROR);
  }
}
