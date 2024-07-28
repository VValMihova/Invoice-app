package bg.softuni.invoice_app.service.companyDetails;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.CompanyDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CompanyDetailsServiceImplTest {
  private final static String TEST_COMPANY_NAME = "Test Company";
  private final static String TEST_ADDRESS = "Test Address";
  private final static String TEST_EIK = "1234567890";
  private final static String TEST_VAT = "BG1234567890";
  private final static String TEST_MANAGER = "Test Manager";
  private final static Long NON_EXIST_ID = 1L;
  
  private CompanyDetailsService toTest;
  
  @Mock
  private CompanyDetailsRepository mockCompanyDetailsRepository;
  
  
  @BeforeEach
  public void setUp() {
    toTest = new CompanyDetailsServiceImpl(mockCompanyDetailsRepository);
  }
  @Test
  public void testAddWithRegistration_Success() {
    CompanyDetails newCompany = new CompanyDetails()
        .setCompanyName(TEST_COMPANY_NAME)
        .setEik(TEST_EIK)
        .setAddress(TEST_ADDRESS)
        .setVatNumber(TEST_VAT)
        .setManager(TEST_MANAGER);
    
    when(mockCompanyDetailsRepository.save(newCompany)).thenReturn(newCompany);
    
    toTest.addWithRegistration(newCompany);
    
    verify(mockCompanyDetailsRepository).save(newCompany);
  }
  
  @Test
  public void testAddWithRegistration_NullCompany() {
    toTest.addWithRegistration(null);
    
    verify(mockCompanyDetailsRepository, never()).save(any(CompanyDetails.class));
  }
  
  
  @Test
  public void testUpdate_Found() {
    Long companyId = 1L;
    CompanyDetailsEditBindingDto companyData = new CompanyDetailsEditBindingDto();
    companyData.setCompanyName("Updated Company")
        .setAddress("Updated Address")
        .setEik("0987654321")
        .setVatNumber("BG0987654321")
        .setManager("Updated Manager");
    
    CompanyDetails existingCompany = new CompanyDetails()
        .setId(companyId)
        .setCompanyName(TEST_COMPANY_NAME)
        .setEik(TEST_EIK)
        .setAddress(TEST_ADDRESS)
        .setVatNumber(TEST_VAT)
        .setManager(TEST_MANAGER);
    
    when(mockCompanyDetailsRepository.findById(companyId)).thenReturn(Optional.of(existingCompany));
    when(mockCompanyDetailsRepository.save(existingCompany)).thenReturn(existingCompany);
    
    CompanyDetails result = toTest.update(companyId, companyData);
    
    assertEquals(companyData.getCompanyName(), result.getCompanyName());
    assertEquals(companyData.getAddress(), result.getAddress());
    assertEquals(companyData.getEik(), result.getEik());
    assertEquals(companyData.getVatNumber(), result.getVatNumber());
    assertEquals(companyData.getManager(), result.getManager());
    
    verify(mockCompanyDetailsRepository).findById(companyId);
    verify(mockCompanyDetailsRepository).save(existingCompany);
  }
  
  @Test
  public void testUpdate_NotFound() {
    Long nonExist = NON_EXIST_ID;
    when(mockCompanyDetailsRepository.findById(nonExist)).thenReturn(Optional.empty());
    
    NotFoundObjectException exception = assertThrows(NotFoundObjectException.class,
        () -> toTest.update(nonExist, new CompanyDetailsEditBindingDto()));
    assertEquals("Company", exception.getObjectType());
    
    verify(mockCompanyDetailsRepository).findById(nonExist);
    verify(mockCompanyDetailsRepository, never()).save(any(CompanyDetails.class));
  }
  
  @Test
  public void testExistsByEik_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByEik(TEST_EIK)).thenReturn(true);
    boolean result = toTest.existsByEik(TEST_EIK);
    assertTrue(result);
    verify(mockCompanyDetailsRepository).existsByEik(TEST_EIK);
  }
  
  @Test
  public void testExistsByEik_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByEik(TEST_EIK)).thenReturn(false);
    boolean result = toTest.existsByEik(TEST_EIK);
    assertFalse(result);
    verify(mockCompanyDetailsRepository).existsByEik(TEST_EIK);
  }
  
  @Test
  public void testExistsByVat_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByVatNumber(TEST_VAT)).thenReturn(true);
    boolean result = toTest.existsByVatNumber(TEST_VAT);
    assertTrue(result);
    verify(mockCompanyDetailsRepository).existsByVatNumber(TEST_VAT);
  }
  
  @Test
  public void testExistsByVat_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByVatNumber(TEST_VAT)).thenReturn(false);
    boolean result = toTest.existsByVatNumber(TEST_VAT);
    assertFalse(result);
    verify(mockCompanyDetailsRepository).existsByVatNumber(TEST_VAT);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByCompanyName(TEST_COMPANY_NAME)).thenReturn(true);
    boolean result = toTest.existsByCompanyName(TEST_COMPANY_NAME);
    assertTrue(result);
    verify(mockCompanyDetailsRepository).existsByCompanyName(TEST_COMPANY_NAME);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByCompanyName(TEST_COMPANY_NAME)).thenReturn(false);
    boolean result = toTest.existsByCompanyName(TEST_COMPANY_NAME);
    assertFalse(result);
    verify(mockCompanyDetailsRepository).existsByCompanyName(TEST_COMPANY_NAME);
  }
}