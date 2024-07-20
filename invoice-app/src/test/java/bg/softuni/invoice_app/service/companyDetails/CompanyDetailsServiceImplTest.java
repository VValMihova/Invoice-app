package bg.softuni.invoice_app.service.companyDetails;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.repository.CompanyDetailsRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CompanyDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CompanyDetailsServiceImplTest {
  @MockBean
  private CompanyDetailsRepository companyDetailsRepository;
  
  @Autowired
  private CompanyDetailsServiceImpl companyDetailsServiceImpl;
  
  @Test
  void testAddWithRegistration() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    when(companyDetailsRepository.save(Mockito.<CompanyDetails>any())).thenReturn(companyDetails);
    
    CompanyDetails companyDetails2 = new CompanyDetails();
    companyDetails2.setAddress("42 Main St");
    companyDetails2.setCompanyName("Company Name");
    companyDetails2.setEik("Eik");
    companyDetails2.setId(1L);
    companyDetails2.setManager("Manager");
    companyDetails2.setVatNumber("42");
    
    // Act
    companyDetailsServiceImpl.addWithRegistration(companyDetails2);
    
    // Assert that nothing has changed
    verify(companyDetailsRepository).save(isA(CompanyDetails.class));
  }
  
  @Test
  void testAddWithRegistration2() {
    // Arrange
    when(companyDetailsRepository.save(Mockito.<CompanyDetails>any()))
        .thenThrow(new NotFoundObjectException("Object Type"));
    
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> companyDetailsServiceImpl.addWithRegistration(companyDetails));
    verify(companyDetailsRepository).save(isA(CompanyDetails.class));
  }
  
  @Test
  void testGetByEik() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    Optional<CompanyDetails> ofResult = Optional.of(companyDetails);
    when(companyDetailsRepository.findByEik(Mockito.<String>any())).thenReturn(ofResult);
    
    // Act
    CompanyDetails actualByEik = companyDetailsServiceImpl.getByEik("Eik");
    
    // Assert
    verify(companyDetailsRepository).findByEik(eq("Eik"));
    assertSame(companyDetails, actualByEik);
  }
  
  @Test
  void testGetByEik2() {
    // Arrange
    when(companyDetailsRepository.findByEik(Mockito.<String>any()))
        .thenThrow(new NotFoundObjectException("Object Type"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> companyDetailsServiceImpl.getByEik("Eik"));
    verify(companyDetailsRepository).findByEik(eq("Eik"));
  }
  
  @Test
  void testGetByName() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    Optional<CompanyDetails> ofResult = Optional.of(companyDetails);
    when(companyDetailsRepository.findByCompanyName(Mockito.<String>any())).thenReturn(ofResult);
    
    // Act
    CompanyDetails actualByName = companyDetailsServiceImpl.getByName("Company Name");
    
    // Assert
    verify(companyDetailsRepository).findByCompanyName(eq("Company Name"));
    assertSame(companyDetails, actualByName);
  }
  
  @Test
  void testGetByName2() {
    // Arrange
    when(companyDetailsRepository.findByCompanyName(Mockito.<String>any()))
        .thenThrow(new NotFoundObjectException("Object Type"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> companyDetailsServiceImpl.getByName("Company Name"));
    verify(companyDetailsRepository).findByCompanyName(eq("Company Name"));
  }
  
  @Test
  void testGetByVatNumber() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    Optional<CompanyDetails> ofResult = Optional.of(companyDetails);
    when(companyDetailsRepository.findByVatNumber(Mockito.<String>any())).thenReturn(ofResult);
    
    // Act
    CompanyDetails actualByVatNumber = companyDetailsServiceImpl.getByVatNumber("Vat");
    
    // Assert
    verify(companyDetailsRepository).findByVatNumber(eq("Vat"));
    assertSame(companyDetails, actualByVatNumber);
  }
  
  @Test
  void testGetByVatNumber2() {
    // Arrange
    when(companyDetailsRepository.findByVatNumber(Mockito.<String>any()))
        .thenThrow(new NotFoundObjectException("Object Type"));
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> companyDetailsServiceImpl.getByVatNumber("Vat"));
    verify(companyDetailsRepository).findByVatNumber(eq("Vat"));
  }
  
  @Test
  void testUpdate() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    Optional<CompanyDetails> ofResult = Optional.of(companyDetails);
    
    CompanyDetails companyDetails2 = new CompanyDetails();
    companyDetails2.setAddress("42 Main St");
    companyDetails2.setCompanyName("Company Name");
    companyDetails2.setEik("Eik");
    companyDetails2.setId(1L);
    companyDetails2.setManager("Manager");
    companyDetails2.setVatNumber("42");
    when(companyDetailsRepository.save(Mockito.<CompanyDetails>any())).thenReturn(companyDetails2);
    when(companyDetailsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    
    CompanyDetailsEditBindingDto companyData = new CompanyDetailsEditBindingDto();
    companyData.setAddress("42 Main St");
    companyData.setCompanyName("Company Name");
    companyData.setEik("Eik");
    companyData.setId(1L);
    companyData.setManager("Manager");
    companyData.setVatNumber("42");
    
    // Act
    CompanyDetails actualUpdateResult = companyDetailsServiceImpl.update(1L, companyData);
    
    // Assert
    verify(companyDetailsRepository).findById(eq(1L));
    verify(companyDetailsRepository).save(isA(CompanyDetails.class));
    assertSame(companyDetails2, actualUpdateResult);
  }
  
  @Test
  void testUpdate2() {
    // Arrange
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setAddress("42 Main St");
    companyDetails.setCompanyName("Company Name");
    companyDetails.setEik("Eik");
    companyDetails.setId(1L);
    companyDetails.setManager("Manager");
    companyDetails.setVatNumber("42");
    Optional<CompanyDetails> ofResult = Optional.of(companyDetails);
    when(companyDetailsRepository.save(Mockito.<CompanyDetails>any()))
        .thenThrow(new NotFoundObjectException("Object Type"));
    when(companyDetailsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    
    CompanyDetailsEditBindingDto companyData = new CompanyDetailsEditBindingDto();
    companyData.setAddress("42 Main St");
    companyData.setCompanyName("Company Name");
    companyData.setEik("Eik");
    companyData.setId(1L);
    companyData.setManager("Manager");
    companyData.setVatNumber("42");
    
    // Act and Assert
    assertThrows(NotFoundObjectException.class, () -> companyDetailsServiceImpl.update(1L, companyData));
    verify(companyDetailsRepository).findById(eq(1L));
    verify(companyDetailsRepository).save(isA(CompanyDetails.class));
  }
}
