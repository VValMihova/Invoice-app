package bg.softuni.invoice_app.service.user;

import bg.softuni.invoice_app.exeption.ErrorMessages;
import bg.softuni.invoice_app.exeption.UserNotFoundException;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.Role;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.model.enums.RoleName;
import bg.softuni.invoice_app.model.user.InvoiceAppUserDetails;
import bg.softuni.invoice_app.repository.UserRepository;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.role.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
  @Spy
  @InjectMocks
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
  
  @Test
  void testGetUser_Found() {
    User user = new User();
    user.setId(TEST_ID);
    user.setEmail(TEST_EMAIL);
    
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getName()).thenReturn(TEST_EMAIL);
    
    when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
    when(mockUserRepository.findById(TEST_ID)).thenReturn(Optional.of(user));
    when(toTest.getCurrentUserId()).thenReturn(TEST_ID);
    
    User result = toTest.getUser();
    
    assertNotNull(result);
    assertEquals(TEST_ID, result.getId());
    assertEquals(TEST_EMAIL, result.getEmail());
    
    verify(mockUserRepository).findById(TEST_ID);
  }
  
  @Test
  void testGetUser_NotFound() {
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getName()).thenReturn(TEST_EMAIL);
    
    when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(new User().setId(TEST_ID)));
    when(mockUserRepository.findById(TEST_ID)).thenReturn(Optional.empty());
    when(toTest.getCurrentUserId()).thenReturn(TEST_ID);
    
    UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> toTest.getUser());
    
    assertEquals(ErrorMessages.USER_NOT_FOUND, exception.getMessage());
    verify(mockUserRepository).findById(TEST_ID);
  }

  
  @Test
  void testFindAllExceptCurrent() {
    String currentUserEmail = TEST_EMAIL;
    String currentUserUUID = UUID.randomUUID().toString();
    
    InvoiceAppUserDetails currentUserDetails = new InvoiceAppUserDetails(currentUserUUID, currentUserEmail, "password", List.of());
    
    Authentication authentication = new UsernamePasswordAuthenticationToken(currentUserDetails, null);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    
    User user1 = new User();
    user1.setEmail(TEST_EMAIL_2);
    
    User user2 = new User();
    user2.setEmail(TEST_EMAIL_3);
    
    List<User> users = List.of(user1, user2);
    Page<User> page = new PageImpl<>(users);
    PageRequest pageRequest = PageRequest.of(0, 10);
    
    when(mockUserRepository.findAllByEmailNot(currentUserEmail, pageRequest)).thenReturn(page);
    
    Page<User> result = toTest.findAllExceptCurrent(pageRequest);
    
    assertNotNull(result);
    assertEquals(2, result.getContent().size());
    assertEquals(TEST_EMAIL_2, result.getContent().get(0).getEmail());
    assertEquals(TEST_EMAIL_3, result.getContent().get(1).getEmail());
    
    verify(mockUserRepository).findAllByEmailNot(currentUserEmail, pageRequest);
  }
  
  @Test
  void testGetCurrentUserId_Found() {
    User user = new User();
    user.setId(TEST_ID);
    user.setEmail(TEST_EMAIL);
    
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getName()).thenReturn(TEST_EMAIL);
    
    when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
    
    Long userId = toTest.getCurrentUserId();
    
    assertNotNull(userId);
    assertEquals(TEST_ID, userId);
    
    verify(mockUserRepository).findByEmail(TEST_EMAIL);
  }
  
  @Test
  void testGetCurrentUserId_NotFound() {
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getName()).thenReturn(TEST_EMAIL);
    
    when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
    
    Long userId = toTest.getCurrentUserId();
    
    assertNull(userId);
    
    verify(mockUserRepository).findByEmail(TEST_EMAIL);
  }
  
  @Test
  void testGetCurrentUserId_NullPrincipal() {
    // Mocking SecurityContext to return null authentication
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(null);
    
    Long userId = toTest.getCurrentUserId();
    
    assertNull(userId);
    
    verify(mockUserRepository, never()).findByEmail(anyString());
  }
  
  
  @Test
  void testUpdateCompany() {
    User user = new User();
    user.setId(TEST_ID);
    user.setEmail(TEST_EMAIL);
    CompanyDetails companyDetails = new CompanyDetails()
        .setCompanyName(COMPANY_NAME)
        .setAddress(COMPANY_ADDRESS)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setManager(COMPANY_MANAGER);
    user.setCompanyDetails(companyDetails);
    
    doReturn(user).when(toTest).getUser();
    
    CompanyDetails updatedCompanyDetails = new CompanyDetails()
        .setCompanyName(UPDATED_COMPANY_NAME)
        .setAddress(UPDATED_COMPANY_ADDRESS)
        .setEik(UPDATED_COMPANY_EIK)
        .setVatNumber(UPDATED_COMPANY_VAT_NUMBER)
        .setManager(UPDATED_COMPANY_MANAGER);
    
    toTest.updateCompany(updatedCompanyDetails);
    
    verify(mockUserRepository).save(user);
    
    assertEquals(UPDATED_COMPANY_NAME, user.getCompanyDetails().getCompanyName());
    assertEquals(UPDATED_COMPANY_ADDRESS, user.getCompanyDetails().getAddress());
    assertEquals(UPDATED_COMPANY_EIK, user.getCompanyDetails().getEik());
    assertEquals(UPDATED_COMPANY_VAT_NUMBER, user.getCompanyDetails().getVatNumber());
    assertEquals(UPDATED_COMPANY_MANAGER, user.getCompanyDetails().getManager());
  }
  
  
  @Test
  void testFindById_Found() {
    User user = new User();
    user.setId(TEST_ID);
    user.setEmail(TEST_EMAIL);
    
    when(mockUserRepository.findById(TEST_ID)).thenReturn(Optional.of(user));
    
    User result = toTest.findById(TEST_ID);
    
    assertNotNull(result);
    assertEquals(TEST_ID, result.getId());
    assertEquals(TEST_EMAIL, result.getEmail());
    
    verify(mockUserRepository).findById(TEST_ID);
  }
  
  @Test
  void testFindById_NotFound() {
    when(mockUserRepository.findById(TEST_ID)).thenReturn(Optional.empty());
    
    UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> toTest.findById(TEST_ID));
    
    assertEquals(ErrorMessages.USER_NOT_FOUND, exception.getMessage());
    verify(mockUserRepository).findById(TEST_ID);
  }
  
  
  @Test
  void testShowCompanyDetails() {
    User user = new User();
    user.setId(TEST_ID);
    user.setEmail(TEST_EMAIL);
    CompanyDetails companyDetails = new CompanyDetails()
        .setCompanyName(COMPANY_NAME)
        .setAddress(COMPANY_ADDRESS)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setManager(COMPANY_MANAGER);
    user.setCompanyDetails(companyDetails);
    CompanyDetailsView companyDetailsView = new CompanyDetailsView();
    companyDetailsView.setCompanyName(COMPANY_NAME);
    companyDetailsView.setAddress(COMPANY_ADDRESS);
    companyDetailsView.setEik(COMPANY_EIK);
    companyDetailsView.setVatNumber(COMPANY_VAT_NUMBER);
    companyDetailsView.setManager(COMPANY_MANAGER);
    
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getName()).thenReturn(TEST_EMAIL);
    
    when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
    when(mockUserRepository.getById(TEST_ID)).thenReturn(user);
    when(mockModelMapper.map(companyDetails, CompanyDetailsView.class)).thenReturn(companyDetailsView);
    
    CompanyDetailsView result = toTest.showCompanyDetails();
    
    assertNotNull(result);
    assertEquals(COMPANY_NAME, result.getCompanyName());
    assertEquals(COMPANY_ADDRESS, result.getAddress());
    assertEquals(COMPANY_EIK, result.getEik());
    assertEquals(COMPANY_VAT_NUMBER, result.getVatNumber());
    assertEquals(COMPANY_MANAGER, result.getManager());
    
    verify(mockUserRepository).getById(TEST_ID);
    verify(mockModelMapper).map(companyDetails, CompanyDetailsView.class);
  }
  
  
  @Test
  void testRegister() {
    UserRegisterBindingDto registerData = new UserRegisterBindingDto();
    registerData.setEmail(TEST_EMAIL);
    registerData.setPassword(TEST_PASSWORD);
    registerData.setCompanyName(COMPANY_NAME);
    registerData.setAddress(COMPANY_ADDRESS);
    registerData.setEik(COMPANY_EIK);
    registerData.setVatNumber(COMPANY_VAT_NUMBER);
    registerData.setManager(COMPANY_MANAGER);
    
    Role userRole = new Role().setName(RoleName.USER);
    User user = new User().setEmail(TEST_EMAIL).setPassword(TEST_PASSWORD);
    new CompanyDetails()
        .setCompanyName(COMPANY_NAME)
        .setAddress(COMPANY_ADDRESS)
        .setEik(COMPANY_EIK)
        .setVatNumber(COMPANY_VAT_NUMBER)
        .setManager(COMPANY_MANAGER);
    
    when(mockRoleService.getRole(RoleName.USER)).thenReturn(Optional.of(userRole));
    when(mockPasswordEncoder.encode(TEST_PASSWORD)).thenReturn(TEST_PASSWORD);
    when(mockUserRepository.save(any(User.class))).thenReturn(user);
    
    toTest.register(registerData);
    
    verify(mockRoleService).getRole(RoleName.USER);
    verify(mockPasswordEncoder).encode(TEST_PASSWORD);
    verify(mockUserRepository, times(2)).save(any(User.class));
    verify(mockCompanyDetailsService).addWithRegistration(any(CompanyDetails.class));
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

