package bg.softuni.invoice_app.service.recipientDetails;

import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.RecipientDetails;

import java.util.List;

public interface RecipientDetailsService {
  boolean exists(RecipientDetails recipientDetails);
  
  List<RecipientDetailsView> findAll();
  
  RecipientDetailsView findById(Long id);
  
  RecipientDetails getById(Long id);
  
  void addRecipientDetails(RecipientDetailsAddDto recipientDetails);
  
  void edit(RecipientDetailsEdit recipientDetailsEdit, Long id);
  
  boolean existsByCompanyName(String companyName);
  
  boolean existsByVatNumber(String vat);
  
  boolean existsByEik(String eik);
}
