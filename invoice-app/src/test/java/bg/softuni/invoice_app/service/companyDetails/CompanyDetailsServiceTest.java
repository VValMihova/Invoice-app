package bg.softuni.invoice_app.service.companyDetails;

import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.CompanyDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CompanyDetailsServiceTest {
  private final CompanyDetails TEST_COMPANY_DETAILS = new CompanyDetails()
        .setCompanyName("Test Company")
        .setEik("1234567890")
        .setAddress("Test Address")
        .setVatNumber("BG1234567890")
        .setManager("Test Manager");
  private final String TEST_EIK = "1234567890";
  private final String TEST_VAT = "BG1234567890";
  private final String TEST_COMPANY_NAME = "Test Company";
  
  private CompanyDetailsService toTest;
  
  @Mock
  private CompanyDetailsRepository mockCompanyDetailsRepository;
  
  
  @BeforeEach
  public void setUp() {
    toTest = new CompanyDetailsServiceImpl(mockCompanyDetailsRepository);
  }
  
  @Test
  public void testExistsByEik_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByEik(TEST_EIK)).thenReturn(true);
    boolean result = toTest.existsByEik(TEST_EIK);
    assertTrue(result);
    verify(mockCompanyDetailsRepository, times(1)).existsByEik(TEST_EIK);
  }
  
  @Test
  public void testExistsByEik_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByEik(TEST_EIK)).thenReturn(false);
    boolean result = toTest.existsByEik(TEST_EIK);
    assertFalse(result);
    verify(mockCompanyDetailsRepository, times(1)).existsByEik(TEST_EIK);
  }
  
  @Test
  public void testExistsByVat_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByVatNumber(TEST_VAT)).thenReturn(true);
    boolean result = toTest.existsByVatNumber(TEST_VAT);
    assertTrue(result);
    verify(mockCompanyDetailsRepository, times(1)).existsByVatNumber(TEST_VAT);
  }
  
  @Test
  public void testExistsByVat_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByVatNumber(TEST_VAT)).thenReturn(false);
    boolean result = toTest.existsByVatNumber(TEST_VAT);
    assertFalse(result);
    verify(mockCompanyDetailsRepository, times(1)).existsByVatNumber(TEST_VAT);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsTrue() {
    when(mockCompanyDetailsRepository.existsByCompanyName(TEST_COMPANY_NAME)).thenReturn(true);
    boolean result = toTest.existsByCompanyName(TEST_COMPANY_NAME);
    assertTrue(result);
    verify(mockCompanyDetailsRepository, times(1)).existsByCompanyName(TEST_COMPANY_NAME);
  }
  
  @Test
  public void testExistsByCompanyName_ReturnsFalse() {
    when(mockCompanyDetailsRepository.existsByCompanyName(TEST_COMPANY_NAME)).thenReturn(false);
    boolean result = toTest.existsByCompanyName(TEST_COMPANY_NAME);
    assertFalse(result);
    verify(mockCompanyDetailsRepository, times(1)).existsByCompanyName(TEST_COMPANY_NAME);
  }
}