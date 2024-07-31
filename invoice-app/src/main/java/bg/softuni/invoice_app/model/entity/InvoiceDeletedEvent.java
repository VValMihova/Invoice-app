package bg.softuni.invoice_app.model.entity;

import org.springframework.context.ApplicationEvent;

public class InvoiceDeletedEvent extends ApplicationEvent {
  private final Invoice invoice;
  
  public InvoiceDeletedEvent(Object source, Invoice invoice) {
    super(source);
    this.invoice = invoice;
  }
  
  public Invoice getInvoice() {
    return invoice;
  }
}