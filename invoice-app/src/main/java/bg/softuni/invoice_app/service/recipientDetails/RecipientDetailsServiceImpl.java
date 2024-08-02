package bg.softuni.invoice_app.service.recipientDetails;

import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.RecipientNotFoundException;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
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
        .findByEikAndUserId(recipientDetails.getVatNumber(), userService.getCurrentUserId())
        .isPresent();
  }
  
  @Override
  public List<RecipientDetailsView> findAll() {
    Optional<List<RecipientDetails>> recipientDetailsList = this.recipientDetailsRepository.findAllByUserId(userService.getCurrentUserId());
    if (recipientDetailsList.isPresent()) {
      return recipientDetailsList.get().stream().map(recipientDetails -> modelMapper.map(recipientDetails, RecipientDetailsView.class)).toList();
    } else {
      throw new RecipientNotFoundException(ErrorMessages.RECIPIENT_NOT_FOUND);
    }
  }
  
  @Override
  public RecipientDetailsView findById(Long id) {
    return this.recipientDetailsRepository.findById(id)
        .map(recipientDetails -> modelMapper.map(recipientDetails, RecipientDetailsView.class))
        .orElseThrow(() -> new RecipientNotFoundException(ErrorMessages.RECIPIENT_NOT_FOUND));
  }
  
  @Override
  public RecipientDetails getById(Long id) {
    return this.recipientDetailsRepository.findById(id)
        .orElseThrow(() -> new RecipientNotFoundException(ErrorMessages.RECIPIENT_NOT_FOUND));
  }
  
  @Override
  public void addRecipientDetails(RecipientDetailsAddDto recipientDetails) {
    recipientDetailsRepository.saveAndFlush(
        modelMapper.map(recipientDetails, RecipientDetails.class)
            .setUser(userService.getUser()));
  }
  
  @Override
  public void edit(RecipientDetailsEdit recipientDetailsEdit, Long id) {
    RecipientDetails recipientDetails = recipientDetailsRepository.findById(id)
        .orElseThrow(() -> new RecipientNotFoundException(ErrorMessages.RECIPIENT_NOT_FOUND));
    recipientDetails.setCompanyName(recipientDetailsEdit.getCompanyName())
        .setAddress(recipientDetailsEdit.getAddress())
        .setEik(recipientDetailsEdit.getEik())
        .setVatNumber(recipientDetailsEdit.getVatNumber())
        .setManager(recipientDetailsEdit.getManager())
        .setUser(userService.getUser());
    recipientDetailsRepository.save(recipientDetails);
  }
  
  @Override
  public boolean existsByCompanyName(String companyName) {
    return recipientDetailsRepository.existsByCompanyName(companyName);
  }
  
  @Override
  public boolean existsByVatNumber(String vat) {
    return recipientDetailsRepository.existsByVatNumber(vat);
  }
  
  @Override
  public boolean existsByEik(String eik) {
    return recipientDetailsRepository.existsByEik(eik);
  }
}
