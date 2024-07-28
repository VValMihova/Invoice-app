package bg.softuni.invoice_app.service.recipientDetails;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipientDetailsServiceImplTest {
  private static final String TEST_ADDRESS = "Test Address";
  private static final String TEST_MANAGER = "Test Manager";
  private final String TEST_EIK = "1234567890";
  private final String TEST_VAT = "BG1234567890";
  private final String TEST_COMPANY_NAME = "Test Company";
  
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
  public void testExistsByEik_ReturnsTrue() {
    when(mockRepository.existsByEik(TEST_EIK)).thenReturn(true);
    boolean result = toTest.existsByEik(TEST_EIK);
    assertTrue(result);
    verify(mockRepository, times(1)).existsByEik(TEST_EIK);
  }
  
  @Test
  public void testExistsByEik_ReturnsFalse() {
    when(mockRepository.existsByEik(TEST_EIK)).thenReturn(false);
    boolean result = toTest.existsByEik(TEST_EIK);
    assertFalse(result);
    verify(mockRepository, times(1)).existsByEik(TEST_EIK);
  }
  
  @Test
  public void testExistsByVat_ReturnsTrue() {
    when(mockRepository.existsByVatNumber(TEST_VAT)).thenReturn(true);
    boolean result = toTest.existsByVatNumber(TEST_VAT);
    assertTrue(result);
    verify(mockRepository, times(1)).existsByVatNumber(TEST_VAT);
  }
  
  @Test
  public void testExistsByVat_ReturnsFalse() {
    when(mockRepository.existsByVatNumber(TEST_VAT)).thenReturn(false);
    boolean result = toTest.existsByVatNumber(TEST_VAT);
    assertFalse(result);
    verify(mockRepository, times(1)).existsByVatNumber(TEST_VAT);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsTrue() {
    when(mockRepository.existsByCompanyName(TEST_COMPANY_NAME)).thenReturn(true);
    boolean result = toTest.existsByCompanyName(TEST_COMPANY_NAME);
    assertTrue(result);
    verify(mockRepository, times(1)).existsByCompanyName(TEST_COMPANY_NAME);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsFalse() {
    when(mockRepository.existsByCompanyName(TEST_COMPANY_NAME)).thenReturn(false);
    boolean result = toTest.existsByCompanyName(TEST_COMPANY_NAME);
    assertFalse(result);
    verify(mockRepository, times(1)).existsByCompanyName(TEST_COMPANY_NAME);
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
    verify(mockRepository, times(1)).findById(1L);
    verify(mockModelMapper, times(1)).map(testRecipient, RecipientDetailsView.class);
  }
  
  @Test
  public void testFindById_NotFound() {
    Assertions.assertThrows(
        NotFoundObjectException.class,
        () -> toTest.findById(1000000000L)
    );
  }
  
  @Test
  public void testGetById_NotFound() {
    Assertions.assertThrows(
        NotFoundObjectException.class,
        () -> toTest.findById(1000000000L)
    );
  }
}
