package bg.softuni.invoice_app.model.dto.recipientDetails;

public interface RecipientDetailsProvider {
  String getCompanyName();
  
  String getEik();
  
  String getVatNumber();
}