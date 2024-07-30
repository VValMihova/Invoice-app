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

import static bg.softuni.invoice_app.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CompanyDetailsServiceImplTest {
  
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
        .setCompanyName(COMPANY_NAME)
        .setEik(COMPANY_EIK)
        .setAddress(COMPANY_ADDRESS)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setManager(COMPANY_MANAGER);
    
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
    companyData.setCompanyName(UPDATED_COMPANY_NAME)
        .setAddress(UPDATED_COMPANY_ADDRESS)
        .setEik(UPDATED_COMPANY_EIK)
        .setVatNumber(UPDATED_COMPANY_VAT_NUMBER)
        .setManager(UPDATED_COMPANY_MANAGER);
    
    CompanyDetails existingCompany = new CompanyDetails()
        .setId(companyId)
        .setCompanyName(COMPANY_NAME)
        .setEik(COMPANY_EIK)
        .setAddress(COMPANY_ADDRESS)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setManager(COMPANY_MANAGER);
    
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
    Long nonExist = TEST_ID;
    when(mockCompanyDetailsRepository.findById(nonExist)).thenReturn(Optional.empty());
    
    NotFoundObjectException exception = assertThrows(NotFoundObjectException.class,
        () -> toTest.update(nonExist, new CompanyDetailsEditBindingDto()));
    assertEquals("Company", exception.getObjectType());
    
    verify(mockCompanyDetailsRepository).findById(nonExist);
    verify(mockCompanyDetailsRepository, never()).save(any(CompanyDetails.class));
  }
  
  @Test
  public void testExistsByEik_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByEik(COMPANY_EIK)).thenReturn(true);
    boolean result = toTest.existsByEik(COMPANY_EIK);
    assertTrue(result);
    verify(mockCompanyDetailsRepository).existsByEik(COMPANY_EIK);
  }
  
  @Test
  public void testExistsByEik_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByEik(COMPANY_EIK)).thenReturn(false);
    boolean result = toTest.existsByEik(COMPANY_EIK);
    assertFalse(result);
    verify(mockCompanyDetailsRepository).existsByEik(COMPANY_EIK);
  }
  
  @Test
  public void testExistsByVat_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByVatNumber(COMPANY_VAT_NUMBER)).thenReturn(true);
    boolean result = toTest.existsByVatNumber(COMPANY_VAT_NUMBER);
    assertTrue(result);
    verify(mockCompanyDetailsRepository).existsByVatNumber(COMPANY_VAT_NUMBER);
  }
  
  @Test
  public void testExistsByVat_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByVatNumber(COMPANY_VAT_NUMBER)).thenReturn(false);
    boolean result = toTest.existsByVatNumber(COMPANY_VAT_NUMBER);
    assertFalse(result);
    verify(mockCompanyDetailsRepository).existsByVatNumber(COMPANY_VAT_NUMBER);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByCompanyName(COMPANY_NAME)).thenReturn(true);
    boolean result = toTest.existsByCompanyName(COMPANY_NAME);
    assertTrue(result);
    verify(mockCompanyDetailsRepository).existsByCompanyName(COMPANY_NAME);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByCompanyName(COMPANY_NAME)).thenReturn(false);
    boolean result = toTest.existsByCompanyName(COMPANY_NAME);
    assertFalse(result);
    verify(mockCompanyDetailsRepository).existsByCompanyName(COMPANY_NAME);
  }
}