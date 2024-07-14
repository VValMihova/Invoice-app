package bg.softuni.invoice_app.service.recipientDetails;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.RecipientDetails;

import java.util.List;

public interface RecipientDetailsService {
  boolean exists(RecipientDetails recipientDetails);
  RecipientDetails getByVatNumber(String vatNumber);
  
  RecipientDetails saveAndReturn(RecipientDetails newRecipient);
  
  List<RecipientDetailsView> findAll(Long id);
}
