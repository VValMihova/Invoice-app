package bg.softuni.invoice_app.service.recipientDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.RecipientDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.RecipientDetailsRepository;
import bg.softuni.invoice_app.service.user.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RecipientDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RecipientDetailsServiceImplTest {
  @MockBean
  private ModelMapper modelMapper;
  
  @MockBean
  private RecipientDetailsRepository recipientDetailsRepository;
  
  @Autowired
  private RecipientDetailsServiceImpl recipientDetailsServiceImpl;
  
  @MockBean
  private UserService userService;
  
  @Test
  void testExists() {
    // Arrange
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    Optional<RecipientDetails> ofResult = Optional.of(recipientDetails);
    when(recipientDetailsRepository.findByEikAndUserId(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    when(userService.getCurrentUserId()).thenReturn(1L);
    
    User user2 = getUser();
    
    RecipientDetails recipientDetails2 = new RecipientDetails();
    recipientDetails2.setAddress("42 Main St");
    recipientDetails2.setCompanyName("Company Name");
    recipientDetails2.setEik("Eik");
    recipientDetails2.setId(1L);
    recipientDetails2.setManager("Manager");
    recipientDetails2.setUser(user2);
    recipientDetails2.setVatNumber("42");
    
    // Act
    boolean actualExistsResult = recipientDetailsServiceImpl.exists(recipientDetails2);
    
    // Assert
    verify(recipientDetailsRepository).findByEikAndUserId(eq("42"), eq(1L));
    verify(userService).getCurrentUserId();
    assertTrue(actualExistsResult);
  }
  
  private static User getUser() {
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    
    User user = new User();
    user.setCompanyDetails(companyDetails);
    user.setEmail("jane.doe@example.org");
    user.setId(1L);
    user.setInvoices(new HashSet<>());
    user.setPassword("iloveyou");
    user.setRecipients(new HashSet<>());
    user.setRoles(new HashSet<>());
    return user;
  }
  
  @Test
  void testExists2() {
    // Arrange
    when(userService.getCurrentUserId()).thenThrow(new NotFoundObjectException("Object Type"));
    
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.exists(recipientDetails));
    verify(userService).getCurrentUserId();
  }
  
  @Test
  void testExists3() {
    // Arrange
    Optional<RecipientDetails> emptyResult = Optional.empty();
    when(recipientDetailsRepository.findByEikAndUserId(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    when(userService.getCurrentUserId()).thenReturn(1L);
    
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    
    // Act
    boolean actualExistsResult = recipientDetailsServiceImpl.exists(recipientDetails);
    
    // Assert
    verify(recipientDetailsRepository).findByEikAndUserId(eq("42"), eq(1L));
    verify(userService).getCurrentUserId();
    assertFalse(actualExistsResult);
  }
  
  @Test
  void testFindAll() {
    // Arrange
    Optional<List<RecipientDetails>> ofResult = Optional.of(new ArrayList<>());
    when(recipientDetailsRepository.findAllByUserId(Mockito.<Long>any())).thenReturn(ofResult);
    when(userService.getCurrentUserId()).thenReturn(1L);
    
    // Act
    List<RecipientDetailsView> actualFindAllResult = recipientDetailsServiceImpl.findAll();
    
    // Assert
    verify(recipientDetailsRepository).findAllByUserId(eq(1L));
    verify(userService).getCurrentUserId();
    assertTrue(actualFindAllResult.isEmpty());
  }
  
  @Test
  void testFindAll2() {
    // Arrange
    when(userService.getCurrentUserId()).thenThrow(new NotFoundObjectException("Object Type"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.findAll());
    verify(userService).getCurrentUserId();
  }
  
  @Test
  void testFindAll3() {
    // Arrange
    Optional<List<RecipientDetails>> ofResult = getRecipientDetails();
    when(recipientDetailsRepository.findAllByUserId(Mockito.<Long>any())).thenReturn(ofResult);
    RecipientDetailsView recipientDetailsView = new RecipientDetailsView();
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RecipientDetailsView>>any()))
        .thenReturn(recipientDetailsView);
    when(userService.getCurrentUserId()).thenReturn(1L);
    
    // Act
    List<RecipientDetailsView> actualFindAllResult = recipientDetailsServiceImpl.findAll();
    
    // Assert
    verify(recipientDetailsRepository).findAllByUserId(eq(1L));
    verify(userService).getCurrentUserId();
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
    assertEquals(1, actualFindAllResult.size());
    assertSame(recipientDetailsView, actualFindAllResult.getFirst());
  }
  
  private static Optional<List<RecipientDetails>> getRecipientDetails() {
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    
    ArrayList<RecipientDetails> recipientDetailsList = new ArrayList<>();
    recipientDetailsList.add(recipientDetails);
    return Optional.of(recipientDetailsList);
  }
  
  @Test
  void testFindAll4() {
    // Arrange
    Optional<List<RecipientDetails>> ofResult = getDetails();
    when(recipientDetailsRepository.findAllByUserId(Mockito.<Long>any())).thenReturn(ofResult);
    RecipientDetailsView recipientDetailsView = new RecipientDetailsView();
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RecipientDetailsView>>any()))
        .thenReturn(recipientDetailsView);
    when(userService.getCurrentUserId()).thenReturn(1L);
    
    // Act
    List<RecipientDetailsView> actualFindAllResult = recipientDetailsServiceImpl.findAll();
    
    // Assert
    verify(recipientDetailsRepository).findAllByUserId(eq(1L));
    verify(userService).getCurrentUserId();
    verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), isA(Class.class));
    assertEquals(2, actualFindAllResult.size());
    RecipientDetailsView getResult = actualFindAllResult.get(1);
    assertNull(getResult.getId());
    assertNull(getResult.getAddress());
    assertNull(getResult.getCompanyName());
    assertNull(getResult.getEik());
    assertNull(getResult.getManager());
    assertNull(getResult.getVatNumber());
    assertSame(recipientDetailsView, actualFindAllResult.get(0));
  }
  
  private static Optional<List<RecipientDetails>> getDetails() {
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    
    User user2 = getUser2();
    
    RecipientDetails recipientDetails2 = new RecipientDetails();
    recipientDetails2.setAddress("17 High St");
    recipientDetails2.setCompanyName("42");
    recipientDetails2.setEik("42");
    recipientDetails2.setId(2L);
    recipientDetails2.setManager("42");
    recipientDetails2.setUser(user2);
    recipientDetails2.setVatNumber("Vat Number");
    
    ArrayList<RecipientDetails> recipientDetailsList = new ArrayList<>();
    recipientDetailsList.add(recipientDetails2);
    recipientDetailsList.add(recipientDetails);
    return Optional.of(recipientDetailsList);
  }
  
  private static User getUser2() {
    CompanyDetails companyDetails2 = new CompanyDetails();
    companyDetails2.setAddress("17 High St");
    companyDetails2.setCompanyName("42");
    companyDetails2.setEik("42");
    companyDetails2.setId(2L);
    companyDetails2.setManager("42");
    companyDetails2.setVatNumber("Vat Number");
    
    User user2 = new User();
    user2.setCompanyDetails(companyDetails2);
    user2.setEmail("john.smith@example.org");
    user2.setId(2L);
    user2.setInvoices(new HashSet<>());
    user2.setPassword("Password");
    user2.setRecipients(new HashSet<>());
    user2.setRoles(new HashSet<>());
    return user2;
  }
  
  @Test
  void testFindById() {
    // Arrange
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    Optional<RecipientDetails> ofResult = Optional.of(recipientDetails);
    when(recipientDetailsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    RecipientDetailsView recipientDetailsView = new RecipientDetailsView();
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RecipientDetailsView>>any()))
        .thenReturn(recipientDetailsView);
    
    // Act
    RecipientDetailsView actualFindByIdResult = recipientDetailsServiceImpl.findById(1L);
    
    // Assert
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
    verify(recipientDetailsRepository).findById(eq(1L));
    assertSame(recipientDetailsView, actualFindByIdResult);
  }
  
  @Test
  void testFindById2() {
    // Arrange
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    Optional<RecipientDetails> ofResult = Optional.of(recipientDetails);
    when(recipientDetailsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RecipientDetailsView>>any()))
        .thenThrow(new NotFoundObjectException("Recipient"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.findById(1L));
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
    verify(recipientDetailsRepository).findById(eq(1L));
  }
  
  @Test
  void testGetById() {
    // Arrange
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    Optional<RecipientDetails> ofResult = Optional.of(recipientDetails);
    when(recipientDetailsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    
    // Act
    RecipientDetails actualById = recipientDetailsServiceImpl.getById(1L);
    
    // Assert
    verify(recipientDetailsRepository).findById(eq(1L));
    assertSame(recipientDetails, actualById);
  }
  
  @Test
  void testGetById2() {
    // Arrange
    Optional<RecipientDetails> emptyResult = Optional.empty();
    when(recipientDetailsRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.getById(1L));
    verify(recipientDetailsRepository).findById(eq(1L));
  }
  
  @Test
  void testGetById3() {
    // Arrange
    when(recipientDetailsRepository.findById(Mockito.<Long>any())).thenThrow(new NotFoundObjectException("Recipient"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.getById(1L));
    verify(recipientDetailsRepository).findById(eq(1L));
  }
  
  @Test
  void testAddRecipientDetails() {
    // Arrange
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    when(recipientDetailsRepository.saveAndFlush(Mockito.<RecipientDetails>any())).thenReturn(recipientDetails);
    
    User user2 = getUser();
    
    RecipientDetails recipientDetails2 = new RecipientDetails();
    recipientDetails2.setAddress("42 Main St");
    recipientDetails2.setCompanyName("Company Name");
    recipientDetails2.setEik("Eik");
    recipientDetails2.setId(1L);
    recipientDetails2.setManager("Manager");
    recipientDetails2.setUser(user2);
    recipientDetails2.setVatNumber("42");
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RecipientDetails>>any())).thenReturn(recipientDetails2);
    
    User user3 = getUser();
    when(userService.getUser()).thenReturn(user3);
    
    RecipientDetailsAddDto recipientDetails3 = new RecipientDetailsAddDto();
    recipientDetails3.setAddress("42 Main St");
    recipientDetails3.setCompanyName("Company Name");
    recipientDetails3.setEik("Eik");
    recipientDetails3.setManager("Manager");
    recipientDetails3.setVatNumber("42");
    
    // Act
    recipientDetailsServiceImpl.addRecipientDetails(recipientDetails3);
    
    // Assert
    verify(userService).getUser();
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
    verify(recipientDetailsRepository).saveAndFlush(isA(RecipientDetails.class));
  }
  
  @Test
  void testAddRecipientDetails2() {
    // Arrange
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RecipientDetails>>any())).thenReturn(recipientDetails);
    when(userService.getUser()).thenThrow(new NotFoundObjectException("Object Type"));
    
    RecipientDetailsAddDto recipientDetails2 = new RecipientDetailsAddDto();
    recipientDetails2.setAddress("42 Main St");
    recipientDetails2.setCompanyName("Company Name");
    recipientDetails2.setEik("Eik");
    recipientDetails2.setManager("Manager");
    recipientDetails2.setVatNumber("42");
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class,
        () -> recipientDetailsServiceImpl.addRecipientDetails(recipientDetails2));
    verify(userService).getUser();
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
  }
  
  @Test
  void testEdit() {
    // Arrange
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    Optional<RecipientDetails> ofResult = Optional.of(recipientDetails);
    
    User user2 = getUser();
    
    RecipientDetails recipientDetails2 = new RecipientDetails();
    recipientDetails2.setAddress("42 Main St");
    recipientDetails2.setCompanyName("Company Name");
    recipientDetails2.setEik("Eik");
    recipientDetails2.setId(1L);
    recipientDetails2.setManager("Manager");
    recipientDetails2.setUser(user2);
    recipientDetails2.setVatNumber("42");
    when(recipientDetailsRepository.save(Mockito.<RecipientDetails>any())).thenReturn(recipientDetails2);
    when(recipientDetailsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    
    User user3 = getUser();
    when(userService.getUser()).thenReturn(user3);
    
    RecipientDetailsEdit recipientDetailsEdit = new RecipientDetailsEdit();
    recipientDetailsEdit.setAddress("42 Main St");
    recipientDetailsEdit.setCompanyName("Company Name");
    recipientDetailsEdit.setEik("Eik");
    recipientDetailsEdit.setId(1L);
    recipientDetailsEdit.setManager("Manager");
    recipientDetailsEdit.setVatNumber("42");
    
    // Act
    recipientDetailsServiceImpl.edit(recipientDetailsEdit, 1L);
    
    // Assert
    verify(userService).getUser();
    verify(recipientDetailsRepository).findById(eq(1L));
    verify(recipientDetailsRepository).save(isA(RecipientDetails.class));
  }
  
  @Test
  void testEdit2() {
    // Arrange
    User user = getUser();
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setAddress("42 Main St");
    recipientDetails.setCompanyName("Company Name");
    recipientDetails.setEik("Eik");
    recipientDetails.setId(1L);
    recipientDetails.setManager("Manager");
    recipientDetails.setUser(user);
    recipientDetails.setVatNumber("42");
    Optional<RecipientDetails> ofResult = Optional.of(recipientDetails);
    when(recipientDetailsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    when(userService.getUser()).thenThrow(new NotFoundObjectException("Object Type"));
    
    RecipientDetailsEdit recipientDetailsEdit = new RecipientDetailsEdit();
    recipientDetailsEdit.setAddress("42 Main St");
    recipientDetailsEdit.setCompanyName("Company Name");
    recipientDetailsEdit.setEik("Eik");
    recipientDetailsEdit.setId(1L);
    recipientDetailsEdit.setManager("Manager");
    recipientDetailsEdit.setVatNumber("42");
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.edit(recipientDetailsEdit, 1L));
    verify(userService).getUser();
    verify(recipientDetailsRepository).findById(eq(1L));
  }
  
  @Test
  void testExistsByCompanyName() {
    // Arrange
    when(recipientDetailsRepository.existsByCompanyName(Mockito.<String>any())).thenReturn(true);
    
    // Act
    boolean actualExistsByCompanyNameResult = recipientDetailsServiceImpl.existsByCompanyName("Company Name");
    
    // Assert
    verify(recipientDetailsRepository).existsByCompanyName(eq("Company Name"));
    assertTrue(actualExistsByCompanyNameResult);
  }
  
  @Test
  void testExistsByCompanyName2() {
    // Arrange
    when(recipientDetailsRepository.existsByCompanyName(Mockito.<String>any())).thenReturn(false);
    
    // Act
    boolean actualExistsByCompanyNameResult = recipientDetailsServiceImpl.existsByCompanyName("Company Name");
    
    // Assert
    verify(recipientDetailsRepository).existsByCompanyName(eq("Company Name"));
    assertFalse(actualExistsByCompanyNameResult);
  }
  
  @Test
  void testExistsByCompanyName3() {
    // Arrange
    when(recipientDetailsRepository.existsByCompanyName(Mockito.<String>any()))
        .thenThrow(new NotFoundObjectException("Object Type"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.existsByCompanyName("Company Name"));
    verify(recipientDetailsRepository).existsByCompanyName(eq("Company Name"));
  }
  
  @Test
  void testExistsByVatNumber() {
    // Arrange
    when(recipientDetailsRepository.existsByVatNumber(Mockito.<String>any())).thenReturn(true);
    
    // Act
    boolean actualExistsByVatNumberResult = recipientDetailsServiceImpl.existsByVatNumber("Vat");
    
    // Assert
    verify(recipientDetailsRepository).existsByVatNumber(eq("Vat"));
    assertTrue(actualExistsByVatNumberResult);
  }
  
  @Test
  void testExistsByVatNumber2() {
    // Arrange
    when(recipientDetailsRepository.existsByVatNumber(Mockito.<String>any())).thenReturn(false);
    
    // Act
    boolean actualExistsByVatNumberResult = recipientDetailsServiceImpl.existsByVatNumber("Vat");
    
    // Assert
    verify(recipientDetailsRepository).existsByVatNumber(eq("Vat"));
    assertFalse(actualExistsByVatNumberResult);
  }
  
  @Test
  void testExistsByVatNumber3() {
    // Arrange
    when(recipientDetailsRepository.existsByVatNumber(Mockito.<String>any()))
        .thenThrow(new NotFoundObjectException("Object Type"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.existsByVatNumber("Vat"));
    verify(recipientDetailsRepository).existsByVatNumber(eq("Vat"));
  }
  
  @Test
  void testExistsByEik() {
    // Arrange
    when(recipientDetailsRepository.existsByEik(Mockito.<String>any())).thenReturn(true);
    
    // Act
    boolean actualExistsByEikResult = recipientDetailsServiceImpl.existsByEik("Eik");
    
    // Assert
    verify(recipientDetailsRepository).existsByEik(eq("Eik"));
    assertTrue(actualExistsByEikResult);
  }
  
  @Test
  void testExistsByEik2() {
    // Arrange
    when(recipientDetailsRepository.existsByEik(Mockito.<String>any())).thenReturn(false);
    
    // Act
    boolean actualExistsByEikResult = recipientDetailsServiceImpl.existsByEik("Eik");
    
    // Assert
    verify(recipientDetailsRepository).existsByEik(eq("Eik"));
    assertFalse(actualExistsByEikResult);
  }
  
  @Test
  void testExistsByEik3() {
    // Arrange
    when(recipientDetailsRepository.existsByEik(Mockito.<String>any()))
        .thenThrow(new NotFoundObjectException("Object Type"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> recipientDetailsServiceImpl.existsByEik("Eik"));
    verify(recipientDetailsRepository).existsByEik(eq("Eik"));
  }
}
