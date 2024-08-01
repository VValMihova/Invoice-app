package bg.softuni.invoice_app.service.recipientDetails;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.RecipientDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.repository.RecipientDetailsRepository;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipientDetailsServiceImplTest {
  
  
  private RecipientDetailsService toTest;
  
  @Mock
  private RecipientDetailsRepository mockRepository;
  
  @Mock
  private UserService mockUserService;
  
  @Mock
  private ModelMapper mockModelMapper;
  
  @BeforeEach
  public void setUp() {
    toTest = new RecipientDetailsServiceImpl(
        mockRepository,
        mockModelMapper,
        mockUserService);
  }
  
  @Test
  void testFindAll_Found() {
    Long userId = TEST_ID;
    RecipientDetails recipient1 = new RecipientDetails()
        .setId(TEST_ID)
        .setCompanyName(COMPANY_NAME)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setAddress(COMPANY_ADDRESS)
        .setManager(COMPANY_MANAGER)
        .setUser(new User().setId(userId));
    
    RecipientDetails recipient2 = new RecipientDetails()
        .setId(TEST_ID_2)
        .setCompanyName(ANOTHER_COMPANY_NAME)
        .setEik(ANOTHER_COMPANY_EIK)
        .setVatNumber(ANOTHER_COMPANY_VAT)
        .setAddress(ANOTHER_COMPANY_ADDRESS)
        .setManager(ANOTHER_COMPANY_MANAGER)
        .setUser(new User().setId(userId));
    
    List<RecipientDetails> recipients = List.of(recipient1, recipient2);
    when(mockUserService.getCurrentUserId()).thenReturn(userId);
    when(mockRepository.findAllByUserId(userId)).thenReturn(Optional.of(recipients));
    when(mockModelMapper.map(recipient1, RecipientDetailsView.class)).thenReturn(new RecipientDetailsView(recipient1));
    when(mockModelMapper.map(recipient2, RecipientDetailsView.class)).thenReturn(new RecipientDetailsView(recipient2));
    
    List<RecipientDetailsView> result = toTest.findAll();
    
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(COMPANY_NAME, result.get(0).getCompanyName());
    assertEquals(COMPANY_ADDRESS, result.get(0).getAddress());
    assertEquals(COMPANY_EIK, result.get(0).getEik());
    assertEquals(COMPANY_VAT_NUMBER, result.get(0).getVatNumber());
    assertEquals(COMPANY_MANAGER, result.get(0).getManager());
    assertEquals(ANOTHER_COMPANY_NAME, result.get(1).getCompanyName());
    assertEquals(ANOTHER_COMPANY_ADDRESS, result.get(1).getAddress());
    assertEquals(ANOTHER_COMPANY_EIK, result.get(1).getEik());
    assertEquals(ANOTHER_COMPANY_VAT, result.get(1).getVatNumber());
    assertEquals(ANOTHER_COMPANY_MANAGER, result.get(1).getManager());
    
    verify(mockUserService).getCurrentUserId();
    verify(mockRepository).findAllByUserId(userId);
    verify(mockModelMapper).map(recipient1, RecipientDetailsView.class);
    verify(mockModelMapper).map(recipient2, RecipientDetailsView.class);
  }
  
  @Test
  void testFindAll_NotFound() {
    Long userId = TEST_ID;
    when(mockUserService.getCurrentUserId()).thenReturn(userId);
    when(mockRepository.findAllByUserId(userId)).thenReturn(Optional.empty());
    
    NotFoundObjectException exception = assertThrows(NotFoundObjectException.class, () -> toTest.findAll());
    assertEquals("Recipient", exception.getObjectType());
    
    verify(mockUserService).getCurrentUserId();
    verify(mockRepository).findAllByUserId(userId);
    verifyNoMoreInteractions(mockRepository, mockModelMapper);
  }
  
  @Test
  void testEdit() {
    Long recipientId = TEST_ID;
    RecipientDetailsEdit editDto = new RecipientDetailsEdit()
        .setCompanyName(UPDATED_COMPANY_NAME)
        .setAddress(UPDATED_COMPANY_ADDRESS)
        .setEik(UPDATED_COMPANY_EIK)
        .setVatNumber(UPDATED_COMPANY_VAT_NUMBER)
        .setManager(UPDATED_COMPANY_MANAGER);
    
    RecipientDetails existingRecipient = new RecipientDetails()
        .setId(recipientId)
        .setCompanyName(COMPANY_NAME)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setAddress(COMPANY_ADDRESS)
        .setManager(COMPANY_MANAGER)
        .setUser(new User());
    
    when(mockRepository.findById(recipientId)).thenReturn(Optional.of(existingRecipient));
    when(mockUserService.getUser()).thenReturn(existingRecipient.getUser());
    
    toTest.edit(editDto, recipientId);
    
    assertEquals(UPDATED_COMPANY_NAME, existingRecipient.getCompanyName());
    assertEquals(UPDATED_COMPANY_EIK, existingRecipient.getEik());
    assertEquals(UPDATED_COMPANY_VAT_NUMBER, existingRecipient.getVatNumber());
    assertEquals(UPDATED_COMPANY_ADDRESS, existingRecipient.getAddress());
    assertEquals(UPDATED_COMPANY_MANAGER, existingRecipient.getManager());
    
    verify(mockRepository).findById(recipientId);
    verify(mockRepository).save(existingRecipient);
  }
  
  
  @Test
  void testEdit_NotFound() {
    Long recipientId = TEST_ID;
    RecipientDetailsEdit editDto = new RecipientDetailsEdit();
    
    when(mockRepository.findById(recipientId)).thenReturn(Optional.empty());
    
    assertThrows(NotFoundObjectException.class, () -> toTest.edit(editDto, recipientId));
    
    verify(mockRepository).findById(recipientId);
    verify(mockRepository, never()).save(any(RecipientDetails.class));
  }
  
  
  @Test
  void testAddRecipientDetails() {
    RecipientDetailsAddDto dto = new RecipientDetailsAddDto()
        .setCompanyName(COMPANY_NAME)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setAddress(COMPANY_ADDRESS)
        .setManager(COMPANY_MANAGER);
    
    User user = new User().setId(TEST_ID);
    RecipientDetails recipientDetails = new RecipientDetails()
        .setCompanyName(COMPANY_NAME)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setAddress(COMPANY_ADDRESS)
        .setManager(COMPANY_MANAGER)
        .setUser(user);
    
    when(mockUserService.getUser()).thenReturn(user);
    when(mockModelMapper.map(dto, RecipientDetails.class)).thenReturn(recipientDetails);
    when(mockRepository.saveAndFlush(recipientDetails)).thenReturn(recipientDetails);
    
    toTest.addRecipientDetails(dto);
    
    verify(mockUserService).getUser();
    verify(mockModelMapper).map(dto, RecipientDetails.class);
    verify(mockRepository).saveAndFlush(recipientDetails);
    
    assertEquals(COMPANY_NAME, recipientDetails.getCompanyName());
    assertEquals(COMPANY_EIK, recipientDetails.getEik());
    assertEquals(COMPANY_VAT_NUMBER, recipientDetails.getVatNumber());
    assertEquals(COMPANY_ADDRESS, recipientDetails.getAddress());
    assertEquals(COMPANY_MANAGER, recipientDetails.getManager());
    assertEquals(user, recipientDetails.getUser());
  }
  
  
  @Test
  void testExists_True() {
    RecipientDetails recipientDetails = new RecipientDetails()
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setUser(new User().setId(TEST_ID));
    
    when(mockUserService.getCurrentUserId()).thenReturn(TEST_ID);
    when(mockRepository.findByEikAndUserId(COMPANY_VAT_NUMBER, TEST_ID)).thenReturn(Optional.of(recipientDetails));
    
    boolean result = toTest.exists(recipientDetails);
    
    assertTrue(result);
    verify(mockUserService).getCurrentUserId();
    verify(mockRepository).findByEikAndUserId(COMPANY_VAT_NUMBER, TEST_ID);
  }
  
  @Test
  void testExists_False() {
    RecipientDetails recipientDetails = new RecipientDetails()
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setUser(new User().setId(TEST_ID));
    
    when(mockUserService.getCurrentUserId()).thenReturn(TEST_ID);
    when(mockRepository.findByEikAndUserId(COMPANY_VAT_NUMBER, TEST_ID)).thenReturn(Optional.empty());
    
    boolean result = toTest.exists(recipientDetails);
    
    assertFalse(result);
    verify(mockUserService).getCurrentUserId();
    verify(mockRepository).findByEikAndUserId(COMPANY_VAT_NUMBER, TEST_ID);
  }
  
  @Test
  public void testExistsByEik_ReturnsTrue() {
    when(mockRepository.existsByEik(COMPANY_EIK)).thenReturn(true);
    boolean result = toTest.existsByEik(COMPANY_EIK);
    assertTrue(result);
    verify(mockRepository).existsByEik(COMPANY_EIK);
  }
  
  @Test
  public void testExistsByEik_ReturnsFalse() {
    when(mockRepository.existsByEik(COMPANY_EIK)).thenReturn(false);
    boolean result = toTest.existsByEik(COMPANY_EIK);
    assertFalse(result);
    verify(mockRepository).existsByEik(COMPANY_EIK);
  }
  
  @Test
  public void testExistsByVat_ReturnsTrue() {
    when(mockRepository.existsByVatNumber(COMPANY_VAT_NUMBER)).thenReturn(true);
    boolean result = toTest.existsByVatNumber(COMPANY_VAT_NUMBER);
    assertTrue(result);
    verify(mockRepository).existsByVatNumber(COMPANY_VAT_NUMBER);
  }
  
  @Test
  public void testExistsByVat_ReturnsFalse() {
    when(mockRepository.existsByVatNumber(COMPANY_VAT_NUMBER)).thenReturn(false);
    boolean result = toTest.existsByVatNumber(COMPANY_VAT_NUMBER);
    assertFalse(result);
    verify(mockRepository).existsByVatNumber(COMPANY_VAT_NUMBER);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsTrue() {
    when(mockRepository.existsByCompanyName(COMPANY_NAME)).thenReturn(true);
    boolean result = toTest.existsByCompanyName(COMPANY_NAME);
    assertTrue(result);
    verify(mockRepository).existsByCompanyName(COMPANY_NAME);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsFalse() {
    when(mockRepository.existsByCompanyName(COMPANY_NAME)).thenReturn(false);
    boolean result = toTest.existsByCompanyName(COMPANY_NAME);
    assertFalse(result);
    verify(mockRepository).existsByCompanyName(COMPANY_NAME);
  }
  
  @Test
  public void testFindById_Found() {
    RecipientDetails testRecipient = new RecipientDetails()
        .setId(TEST_ID)
        .setCompanyName(COMPANY_NAME)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setAddress(COMPANY_ADDRESS)
        .setManager(COMPANY_MANAGER)
        .setUser(new User());
    
    when(mockRepository.findById(TEST_ID))
        .thenReturn(Optional.of(testRecipient));
    when(mockModelMapper.map(testRecipient, RecipientDetailsView.class))
        .thenReturn(new RecipientDetailsView(testRecipient));
    
    RecipientDetailsView recipientDetailsView = toTest.findById(TEST_ID);
    Assertions.assertNotNull(recipientDetailsView);
    assertEquals(COMPANY_NAME, recipientDetailsView.getCompanyName());
    assertEquals(COMPANY_EIK, recipientDetailsView.getEik());
    assertEquals(COMPANY_VAT_NUMBER, recipientDetailsView.getVatNumber());
    assertEquals(COMPANY_ADDRESS, recipientDetailsView.getAddress());
    assertEquals(COMPANY_MANAGER, recipientDetailsView.getManager());
    verify(mockRepository).findById(TEST_ID);
    verify(mockModelMapper).map(testRecipient, RecipientDetailsView.class);
  }
  
  @Test
  public void testFindById_NotFound() {
    Assertions.assertThrows(
        NotFoundObjectException.class,
        () -> toTest.findById(1000000000L)
    );
  }
  
  @Test
  void testGetById_Found() {
    Long recipientId = TEST_ID;
    RecipientDetails existingRecipient = new RecipientDetails()
        .setId(recipientId)
        .setCompanyName(COMPANY_NAME)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setAddress(COMPANY_ADDRESS)
        .setManager(COMPANY_MANAGER)
        .setUser(new User().setId(TEST_ID));
    
    when(mockRepository.findById(recipientId)).thenReturn(Optional.of(existingRecipient));
    
    RecipientDetails result = toTest.getById(recipientId);
    
    assertNotNull(result);
    assertEquals(COMPANY_NAME, result.getCompanyName());
    assertEquals(COMPANY_EIK, result.getEik());
    assertEquals(COMPANY_VAT_NUMBER, result.getVatNumber());
    assertEquals(COMPANY_ADDRESS, result.getAddress());
    assertEquals(COMPANY_MANAGER, result.getManager());
    assertEquals(TEST_ID, result.getUser().getId());
    
    verify(mockRepository).findById(recipientId);
  }
  
  @Test
  void testGetById_NotFound() {
    Long nonExistRecipientId = TEST_ID;
    when(mockRepository.findById(nonExistRecipientId)).thenReturn(Optional.empty());
    
    NotFoundObjectException exception = assertThrows(NotFoundObjectException.class,
        () -> toTest.getById(nonExistRecipientId));
    assertEquals("Recipient", exception.getObjectType());
    
    verify(mockRepository).findById(nonExistRecipientId);
  }
}
