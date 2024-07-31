package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.exeption.DatabaseException;
import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.role.RoleService;
import bg.softuni.invoice_app.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static bg.softuni.invoice_app.util.TestConstants.*;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
  private UserServiceImpl toTest;
  
  @Mock
  private UserRepository mockUserRepository;
  
  @Mock
  private PasswordEncoder mockPasswordEncoder;
  
  @Mock
  private ModelMapper mockModelMapper;
  
  @Mock
  private CompanyDetailsService mockCompanyDetailsService;
  
  @Mock
  private RoleService mockRoleService;
  @Mock
  private Principal mockPrincipal;
  
  @BeforeEach
  void setUp() {
    toTest = new UserServiceImpl(mockUserRepository, mockPasswordEncoder, mockModelMapper, mockCompanyDetailsService, mockRoleService);
  }
  
  
  @Test
  void testGetUserByCompanyVat_Found() {
    User user = new User();
    user.setEmail(TEST_EMAIL);
    user.setPassword(TEST_PASSWORD);
    user.setCompanyDetails(new CompanyDetails()
        .setCompanyName(COMPANY_NAME)
        .setAddress(COMPANY_ADDRESS)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setManager(COMPANY_MANAGER));
    
    when(mockUserRepository.findByCompanyDetailsVatNumber(COMPANY_VAT_NUMBER)).thenReturn(Optional.of(user));
    
    User result = toTest.getUserByCompanyVat(COMPANY_VAT_NUMBER);
    
    assertNotNull(result);
    assertEquals(TEST_EMAIL, result.getEmail());
    assertEquals(COMPANY_NAME, result.getCompanyDetails().getCompanyName());
    assertEquals(COMPANY_ADDRESS, result.getCompanyDetails().getAddress());
    assertEquals(COMPANY_EIK, result.getCompanyDetails().getEik());
    assertEquals(COMPANY_VAT_NUMBER, result.getCompanyDetails().getVatNumber());
    assertEquals(COMPANY_MANAGER, result.getCompanyDetails().getManager());
    
    verify(mockUserRepository).findByCompanyDetailsVatNumber(COMPANY_VAT_NUMBER);
  }
  
  @Test
  void testGetUserByCompanyVat_NotFound() {
    when(mockUserRepository.findByCompanyDetailsVatNumber(COMPANY_VAT_NUMBER)).thenReturn(Optional.empty());
    
    User result = toTest.getUserByCompanyVat(COMPANY_VAT_NUMBER);
    
    assertNull(result);
    verify(mockUserRepository).findByCompanyDetailsVatNumber(COMPANY_VAT_NUMBER);
  }
  @Test
  void testGetUserByCompanyEik_Found() {
    User user = new User();
    user.setEmail(TEST_EMAIL);
    user.setPassword(TEST_PASSWORD);
    user.setCompanyDetails(new CompanyDetails()
        .setCompanyName(COMPANY_NAME)
        .setAddress(COMPANY_ADDRESS)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setManager(COMPANY_MANAGER));
    
    when(mockUserRepository.findByCompanyDetailsEik(COMPANY_EIK)).thenReturn(Optional.of(user));
    
    User result = toTest.getUserByCompanyEik(COMPANY_EIK);
    
    assertNotNull(result);
    assertEquals(TEST_EMAIL, result.getEmail());
    assertEquals(COMPANY_NAME, result.getCompanyDetails().getCompanyName());
    assertEquals(COMPANY_ADDRESS, result.getCompanyDetails().getAddress());
    assertEquals(COMPANY_EIK, result.getCompanyDetails().getEik());
    assertEquals(COMPANY_VAT_NUMBER, result.getCompanyDetails().getVatNumber());
    assertEquals(COMPANY_MANAGER, result.getCompanyDetails().getManager());
    
    verify(mockUserRepository).findByCompanyDetailsEik(COMPANY_EIK);
  }
  
  @Test
  void testGetUserByCompanyEik_NotFound() {
    when(mockUserRepository.findByCompanyDetailsEik(COMPANY_EIK)).thenReturn(Optional.empty());
    
    User result = toTest.getUserByCompanyEik(COMPANY_EIK);
    
    assertNull(result);
    verify(mockUserRepository).findByCompanyDetailsEik(COMPANY_EIK);
  }
  @Test
  void testGetUserByEmail_Found() {
    User user = new User();
    user.setEmail(TEST_EMAIL);
    user.setPassword(TEST_PASSWORD);
    user.setCompanyDetails(new CompanyDetails()
        .setCompanyName(COMPANY_NAME)
        .setAddress(COMPANY_ADDRESS)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setManager(COMPANY_MANAGER));
    
    when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
    
    User result = toTest.getUserByEmail(TEST_EMAIL);
    
    assertNotNull(result);
    assertEquals(TEST_EMAIL, result.getEmail());
    assertEquals(TEST_PASSWORD, result.getPassword());
    assertEquals(COMPANY_NAME, result.getCompanyDetails().getCompanyName());
    assertEquals(COMPANY_ADDRESS, result.getCompanyDetails().getAddress());
    assertEquals(COMPANY_EIK, result.getCompanyDetails().getEik());
    assertEquals(COMPANY_VAT_NUMBER, result.getCompanyDetails().getVatNumber());
    assertEquals(COMPANY_MANAGER, result.getCompanyDetails().getManager());
    
    verify(mockUserRepository).findByEmail(TEST_EMAIL);
  }
  
  @Test
  void testGetUserByEmail_NotFound() {
    when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
    
    User result = toTest.getUserByEmail(TEST_EMAIL);
    
    assertNull(result);
    verify(mockUserRepository).findByEmail(TEST_EMAIL);
  }
}

