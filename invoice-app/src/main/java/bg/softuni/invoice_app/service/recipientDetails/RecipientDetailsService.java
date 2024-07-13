package bg.softuni.invoice_app.service.recipientDetails;

import bg.softuni.invoice_app.model.entity.RecipientDetails;

public interface RecipientDetailsService {
  boolean exists(RecipientDetails recipientDetails);
  RecipientDetails getByVatNumber(String vatNumber);
  
  RecipientDetails saveAndReturn(RecipientDetails newRecipient);
}
