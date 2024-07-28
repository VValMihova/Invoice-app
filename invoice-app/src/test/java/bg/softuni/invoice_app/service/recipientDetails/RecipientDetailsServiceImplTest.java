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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipientDetailsServiceImplTest {
  private static final String TEST_COMPANY_NAME = "Test Company";
  private static final String TEST_ADDRESS = "Test Address";
  private static final String TEST_EIK = "1234567890";
  private static final String TEST_VAT = "BG1234567890";
  private static final String TEST_MANAGER = "Test Manager";
  
  private static final String UPDATED_COMPANY_NAME = "Updated Company";
  private static final String UPDATED_ADDRESS = "Updated Address";
  private static final String UPDATED_EIK = "0987654321";
  private static final String UPDATED_VAT = "BG0987654321";
  private static final String UPDATED_MANAGER = "Updated Manager";
  
  private static final String ANOTHER_COMPANY_NAME = "Another Company";
  private static final String ANOTHER_ADDRESS = "Another Address";
  private static final String ANOTHER_EIK = "0987654321";
  private static final String ANOTHER_VAT = "BG0987654321";
  private static final String ANOTHER_MANAGER = "Another Manager";
  
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
    Long userId = 1L;
    RecipientDetails recipient1 = new RecipientDetails()
        .setId(1L)
        .setCompanyName(TEST_COMPANY_NAME)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setAddress(TEST_ADDRESS)
        .setManager(TEST_MANAGER)
        .setUser(new User().setId(userId));
    
    RecipientDetails recipient2 = new RecipientDetails()
        .setId(2L)
        .setCompanyName(ANOTHER_COMPANY_NAME)
        .setEik(ANOTHER_EIK)
        .setVatNumber(ANOTHER_VAT)
        .setAddress(ANOTHER_ADDRESS)
        .setManager(ANOTHER_MANAGER)
        .setUser(new User().setId(userId));
    
    List<RecipientDetails> recipients = List.of(recipient1, recipient2);
    when(mockUserService.getCurrentUserId()).thenReturn(userId);
    when(mockRepository.findAllByUserId(userId)).thenReturn(Optional.of(recipients));
    when(mockModelMapper.map(recipient1, RecipientDetailsView.class)).thenReturn(new RecipientDetailsView(recipient1));
    when(mockModelMapper.map(recipient2, RecipientDetailsView.class)).thenReturn(new RecipientDetailsView(recipient2));
    
    List<RecipientDetailsView> result = toTest.findAll();
    
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(TEST_COMPANY_NAME, result.get(0).getCompanyName());
    assertEquals(TEST_ADDRESS, result.get(0).getAddress());
    assertEquals(TEST_EIK, result.get(0).getEik());
    assertEquals(TEST_VAT, result.get(0).getVatNumber());
    assertEquals(TEST_MANAGER, result.get(0).getManager());
    assertEquals(ANOTHER_COMPANY_NAME, result.get(1).getCompanyName());
    assertEquals(ANOTHER_ADDRESS, result.get(1).getAddress());
    assertEquals(ANOTHER_EIK, result.get(1).getEik());
    assertEquals(ANOTHER_VAT, result.get(1).getVatNumber());
    assertEquals(ANOTHER_MANAGER, result.get(1).getManager());
    
    verify(mockUserService).getCurrentUserId();
    verify(mockRepository).findAllByUserId(userId);
    verify(mockModelMapper).map(recipient1, RecipientDetailsView.class);
    verify(mockModelMapper).map(recipient2, RecipientDetailsView.class);
  }
  
  @Test
  void testFindAll_NotFound() {
    Long userId = 1L;
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
    Long recipientId = 1L;
    RecipientDetailsEdit editDto = new RecipientDetailsEdit()
        .setCompanyName(UPDATED_COMPANY_NAME)
        .setAddress(UPDATED_ADDRESS)
        .setEik(UPDATED_EIK)
        .setVatNumber(UPDATED_VAT)
        .setManager(UPDATED_MANAGER);
    
    RecipientDetails existingRecipient = new RecipientDetails()
        .setId(recipientId)
        .setCompanyName(TEST_COMPANY_NAME)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setAddress(TEST_ADDRESS)
        .setManager(TEST_MANAGER)
        .setUser(new User());
    
    when(mockRepository.findById(recipientId)).thenReturn(Optional.of(existingRecipient));
    when(mockUserService.getUser()).thenReturn(existingRecipient.getUser());
    
    toTest.edit(editDto, recipientId);
    
    assertEquals(UPDATED_COMPANY_NAME, existingRecipient.getCompanyName());
    assertEquals(UPDATED_EIK, existingRecipient.getEik());
    assertEquals(UPDATED_VAT, existingRecipient.getVatNumber());
    assertEquals(UPDATED_ADDRESS, existingRecipient.getAddress());
    assertEquals(UPDATED_MANAGER, existingRecipient.getManager());
    
    verify(mockRepository).findById(recipientId);
    verify(mockRepository).save(existingRecipient);
  }
  
  
  @Test
  void testEdit_NotFound() {
    Long recipientId = 1L;
    RecipientDetailsEdit editDto = new RecipientDetailsEdit();
    
    when(mockRepository.findById(recipientId)).thenReturn(Optional.empty());
    
    assertThrows(NotFoundObjectException.class, () -> toTest.edit(editDto, recipientId));
    
    verify(mockRepository).findById(recipientId);
    verify(mockRepository, never()).save(any(RecipientDetails.class));
  }
  
  
  @Test
  void testAddRecipientDetails() {
    RecipientDetailsAddDto dto = new RecipientDetailsAddDto()
        .setCompanyName(TEST_COMPANY_NAME)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setAddress(TEST_ADDRESS)
        .setManager(TEST_MANAGER);
    
    User user = new User().setId(1L);
    RecipientDetails recipientDetails = new RecipientDetails()
        .setCompanyName(TEST_COMPANY_NAME)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setAddress(TEST_ADDRESS)
        .setManager(TEST_MANAGER)
        .setUser(user);
    
    when(mockUserService.getUser()).thenReturn(user);
    when(mockModelMapper.map(dto, RecipientDetails.class)).thenReturn(recipientDetails);
    when(mockRepository.saveAndFlush(recipientDetails)).thenReturn(recipientDetails);
    
    toTest.addRecipientDetails(dto);
    
    verify(mockUserService).getUser();
    verify(mockModelMapper).map(dto, RecipientDetails.class);
    verify(mockRepository).saveAndFlush(recipientDetails);
    
    assertEquals(TEST_COMPANY_NAME, recipientDetails.getCompanyName());
    assertEquals(TEST_EIK, recipientDetails.getEik());
    assertEquals(TEST_VAT, recipientDetails.getVatNumber());
    assertEquals(TEST_ADDRESS, recipientDetails.getAddress());
    assertEquals(TEST_MANAGER, recipientDetails.getManager());
    assertEquals(user, recipientDetails.getUser());
  }
  
  
  @Test
  void testExists_True() {
    RecipientDetails recipientDetails = new RecipientDetails()
        .setVatNumber(TEST_VAT)
        .setUser(new User().setId(1L));
    
    when(mockUserService.getCurrentUserId()).thenReturn(1L);
    when(mockRepository.findByEikAndUserId(TEST_VAT, 1L)).thenReturn(Optional.of(recipientDetails));
    
    boolean result = toTest.exists(recipientDetails);
    
    assertTrue(result);
    verify(mockUserService).getCurrentUserId();
    verify(mockRepository).findByEikAndUserId(TEST_VAT, 1L);
  }
  
  @Test
  void testExists_False() {
    RecipientDetails recipientDetails = new RecipientDetails()
        .setVatNumber(TEST_VAT)
        .setUser(new User().setId(1L));
    
    when(mockUserService.getCurrentUserId()).thenReturn(1L);
    when(mockRepository.findByEikAndUserId(TEST_VAT, 1L)).thenReturn(Optional.empty());
    
    boolean result = toTest.exists(recipientDetails);
    
    assertFalse(result);
    verify(mockUserService).getCurrentUserId();
    verify(mockRepository).findByEikAndUserId(TEST_VAT, 1L);
  }
  
  @Test
  public void testExistsByEik_ReturnsTrue() {
    when(mockRepository.existsByEik(TEST_EIK)).thenReturn(true);
    boolean result = toTest.existsByEik(TEST_EIK);
    assertTrue(result);
    verify(mockRepository).existsByEik(TEST_EIK);
  }
  
  @Test
  public void testExistsByEik_ReturnsFalse() {
    when(mockRepository.existsByEik(TEST_EIK)).thenReturn(false);
    boolean result = toTest.existsByEik(TEST_EIK);
    assertFalse(result);
    verify(mockRepository).existsByEik(TEST_EIK);
  }
  
  @Test
  public void testExistsByVat_ReturnsTrue() {
    when(mockRepository.existsByVatNumber(TEST_VAT)).thenReturn(true);
    boolean result = toTest.existsByVatNumber(TEST_VAT);
    assertTrue(result);
    verify(mockRepository).existsByVatNumber(TEST_VAT);
  }
  
  @Test
  public void testExistsByVat_ReturnsFalse() {
    when(mockRepository.existsByVatNumber(TEST_VAT)).thenReturn(false);
    boolean result = toTest.existsByVatNumber(TEST_VAT);
    assertFalse(result);
    verify(mockRepository).existsByVatNumber(TEST_VAT);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsTrue() {
    when(mockRepository.existsByCompanyName(TEST_COMPANY_NAME)).thenReturn(true);
    boolean result = toTest.existsByCompanyName(TEST_COMPANY_NAME);
    assertTrue(result);
    verify(mockRepository).existsByCompanyName(TEST_COMPANY_NAME);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsFalse() {
    when(mockRepository.existsByCompanyName(TEST_COMPANY_NAME)).thenReturn(false);
    boolean result = toTest.existsByCompanyName(TEST_COMPANY_NAME);
    assertFalse(result);
    verify(mockRepository).existsByCompanyName(TEST_COMPANY_NAME);
  }
  
  @Test
  public void testFindById_Found() {
    RecipientDetails testRecipient = new RecipientDetails()
        .setId(1L)
        .setCompanyName(TEST_COMPANY_NAME)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setAddress(TEST_ADDRESS)
        .setManager(TEST_MANAGER)
        .setUser(new User());
    
    when(mockRepository.findById(1L))
        .thenReturn(Optional.of(testRecipient));
    when(mockModelMapper.map(testRecipient, RecipientDetailsView.class))
        .thenReturn(new RecipientDetailsView(testRecipient));
    
    RecipientDetailsView recipientDetailsView = toTest.findById(1L);
    Assertions.assertNotNull(recipientDetailsView);
    assertEquals(TEST_COMPANY_NAME, recipientDetailsView.getCompanyName());
    assertEquals(TEST_EIK, recipientDetailsView.getEik());
    assertEquals(TEST_VAT, recipientDetailsView.getVatNumber());
    assertEquals(TEST_ADDRESS, recipientDetailsView.getAddress());
    assertEquals(TEST_MANAGER, recipientDetailsView.getManager());
    verify(mockRepository).findById(1L);
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
    Long recipientId = 1L;
    RecipientDetails existingRecipient = new RecipientDetails()
        .setId(recipientId)
        .setCompanyName(TEST_COMPANY_NAME)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setAddress(TEST_ADDRESS)
        .setManager(TEST_MANAGER)
        .setUser(new User().setId(1L));
    
    when(mockRepository.findById(recipientId)).thenReturn(Optional.of(existingRecipient));
    
    RecipientDetails result = toTest.getById(recipientId);
    
    assertNotNull(result);
    assertEquals(TEST_COMPANY_NAME, result.getCompanyName());
    assertEquals(TEST_EIK, result.getEik());
    assertEquals(TEST_VAT, result.getVatNumber());
    assertEquals(TEST_ADDRESS, result.getAddress());
    assertEquals(TEST_MANAGER, result.getManager());
    assertEquals(1L, result.getUser().getId());
    
    verify(mockRepository).findById(recipientId);
  }
  
  @Test
  void testGetById_NotFound() {
    Long nonExistRecipientId = 1L;
    when(mockRepository.findById(nonExistRecipientId)).thenReturn(Optional.empty());
    
    NotFoundObjectException exception = assertThrows(NotFoundObjectException.class,
        () -> toTest.getById(nonExistRecipientId));
    assertEquals("Recipient", exception.getObjectType());
    
    verify(mockRepository).findById(nonExistRecipientId);
  }
}
