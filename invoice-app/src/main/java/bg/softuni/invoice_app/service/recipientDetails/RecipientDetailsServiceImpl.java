package bg.softuni.invoice_app.service.recipientDetails;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.RecipientDetails;
import bg.softuni.invoice_app.repository.RecipientDetailsRepository;
import bg.softuni.invoice_app.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipientDetailsServiceImpl implements RecipientDetailsService {
private final RecipientDetailsRepository recipientDetailsRepository;
private final ModelMapper modelMapper;
private final UserService userService;
  
  public RecipientDetailsServiceImpl(
      RecipientDetailsRepository recipientDetailsRepository,
      ModelMapper modelMapper,
      UserService userService) {
    this.recipientDetailsRepository = recipientDetailsRepository;
    this.modelMapper = modelMapper;
    this.userService = userService;
  }
  
  @Override
  public boolean exists(RecipientDetails recipientDetails) {
    return this.recipientDetailsRepository
        .findByVatNumberAndUserId(recipientDetails.getVatNumber(),userService.getCurrentUserId())
        .isPresent();
  }
  
  @Override
  public RecipientDetails getByVatNumber(String vatNumber) {
    return this.recipientDetailsRepository.findByVatNumber(vatNumber).orElse(null);
  }
  
  @Override
  public RecipientDetails saveAndReturn(RecipientDetails newRecipient) {
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setVatNumber(newRecipient.getVatNumber());
    recipientDetails.setUser(userService.getUser());
    recipientDetails.setCompanyName(newRecipient.getCompanyName());
    recipientDetails.setAddress(newRecipient.getAddress());
    recipientDetails.setEik(newRecipient.getEik());
    recipientDetails.setManager(newRecipient.getManager());
    return this.recipientDetailsRepository.save(recipientDetails);
  }
  
  @Override
  public List<RecipientDetailsView> findAll(Long id) {
    Optional<List<RecipientDetails>> recipientDetailsList = this.recipientDetailsRepository.findAllByUserId(id);
    if (recipientDetailsList.isPresent()) {
      return recipientDetailsList.get().stream().map(recipientDetails -> modelMapper.map(recipientDetails, RecipientDetailsView.class)).toList();
    }else {
      throw new NotFoundObjectException("Recipient");
    }
  }
  
  @Override
  public RecipientDetailsView findById(Long id) {
    return this.recipientDetailsRepository.findById(id)
        .map(recipientDetails -> modelMapper.map(recipientDetails, RecipientDetailsView.class))
        .orElseThrow(() -> new NotFoundObjectException("Recipient"));
  }
  
  @Override
  public RecipientDetails getById(Long id) {
    return this.recipientDetailsRepository.findById(id)
        .orElseThrow(() -> new NotFoundObjectException("Recipient"));
  }
}
