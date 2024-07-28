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

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
  private static final String TEST_EMAIL = "test@example.com";
  private static final String TEST_PASSWORD = "password123";
  private static final String TEST_COMPANY_NAME = "Test Company";
  private static final String TEST_ADDRESS = "Test Address";
  private static final String TEST_EIK = "1234567890";
  private static final String TEST_VAT = "BG1234567890";
  private static final String TEST_MANAGER = "Test Manager";
  private static final String NEW_COMPANY_NAME = "New Company";
  private static final String NEW_ADDRESS = "New Address";
  private static final String NEW_EIK = "0987654321";
  private static final String NEW_VAT = "BG0987654321";
  private static final String NEW_MANAGER = "New Manager";
  
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
        .setCompanyName(TEST_COMPANY_NAME)
        .setAddress(TEST_ADDRESS)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setManager(TEST_MANAGER));
    
    when(mockUserRepository.findByCompanyDetailsVatNumber(TEST_VAT)).thenReturn(Optional.of(user));
    
    User result = toTest.getUserByCompanyVat(TEST_VAT);
    
    assertNotNull(result);
    assertEquals(TEST_EMAIL, result.getEmail());
    assertEquals(TEST_COMPANY_NAME, result.getCompanyDetails().getCompanyName());
    assertEquals(TEST_ADDRESS, result.getCompanyDetails().getAddress());
    assertEquals(TEST_EIK, result.getCompanyDetails().getEik());
    assertEquals(TEST_VAT, result.getCompanyDetails().getVatNumber());
    assertEquals(TEST_MANAGER, result.getCompanyDetails().getManager());
    
    verify(mockUserRepository).findByCompanyDetailsVatNumber(TEST_VAT);
  }
  
  @Test
  void testGetUserByCompanyVat_NotFound() {
    when(mockUserRepository.findByCompanyDetailsVatNumber(TEST_VAT)).thenReturn(Optional.empty());
    
    User result = toTest.getUserByCompanyVat(TEST_VAT);
    
    assertNull(result);
    verify(mockUserRepository).findByCompanyDetailsVatNumber(TEST_VAT);
  }
  @Test
  void testGetUserByCompanyEik_Found() {
    User user = new User();
    user.setEmail(TEST_EMAIL);
    user.setPassword(TEST_PASSWORD);
    user.setCompanyDetails(new CompanyDetails()
        .setCompanyName(TEST_COMPANY_NAME)
        .setAddress(TEST_ADDRESS)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setManager(TEST_MANAGER));
    
    when(mockUserRepository.findByCompanyDetailsEik(TEST_EIK)).thenReturn(Optional.of(user));
    
    User result = toTest.getUserByCompanyEik(TEST_EIK);
    
    assertNotNull(result);
    assertEquals(TEST_EMAIL, result.getEmail());
    assertEquals(TEST_COMPANY_NAME, result.getCompanyDetails().getCompanyName());
    assertEquals(TEST_ADDRESS, result.getCompanyDetails().getAddress());
    assertEquals(TEST_EIK, result.getCompanyDetails().getEik());
    assertEquals(TEST_VAT, result.getCompanyDetails().getVatNumber());
    assertEquals(TEST_MANAGER, result.getCompanyDetails().getManager());
    
    verify(mockUserRepository).findByCompanyDetailsEik(TEST_EIK);
  }
  
  @Test
  void testGetUserByCompanyEik_NotFound() {
    when(mockUserRepository.findByCompanyDetailsEik(TEST_EIK)).thenReturn(Optional.empty());
    
    User result = toTest.getUserByCompanyEik(TEST_EIK);
    
    assertNull(result);
    verify(mockUserRepository).findByCompanyDetailsEik(TEST_EIK);
  }
  @Test
  void testGetUserByEmail_Found() {
    User user = new User();
    user.setEmail(TEST_EMAIL);
    user.setPassword(TEST_PASSWORD);
    user.setCompanyDetails(new CompanyDetails()
        .setCompanyName(TEST_COMPANY_NAME)
        .setAddress(TEST_ADDRESS)
        .setEik(TEST_EIK)
        .setVatNumber(TEST_VAT)
        .setManager(TEST_MANAGER));
    
    when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
    
    User result = toTest.getUserByEmail(TEST_EMAIL);
    
    assertNotNull(result);
    assertEquals(TEST_EMAIL, result.getEmail());
    assertEquals(TEST_PASSWORD, result.getPassword());
    assertEquals(TEST_COMPANY_NAME, result.getCompanyDetails().getCompanyName());
    assertEquals(TEST_ADDRESS, result.getCompanyDetails().getAddress());
    assertEquals(TEST_EIK, result.getCompanyDetails().getEik());
    assertEquals(TEST_VAT, result.getCompanyDetails().getVatNumber());
    assertEquals(TEST_MANAGER, result.getCompanyDetails().getManager());
    
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

