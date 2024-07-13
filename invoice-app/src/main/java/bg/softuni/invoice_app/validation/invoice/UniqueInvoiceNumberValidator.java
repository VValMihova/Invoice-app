package bg.softuni.invoice_app.validation.invoice;

import bg.softuni.invoice_app.service.invoice.InvoiceService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueInvoiceNumberValidator implements ConstraintValidator<UniqueInvoiceNumber, Long> {
  private final InvoiceService invoiceService;
  
  public UniqueInvoiceNumberValidator(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }
  
  @Override
  public boolean isValid(Long invoiceNumber, ConstraintValidatorContext constraintValidatorContext) {
    return !invoiceService.checkInvoiceExists(invoiceNumber);
  }
}
