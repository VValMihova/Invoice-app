package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HomeControllerTest {
  
  @Mock
  private UserService userService;
  
  @Mock
  private Model model;
  
  @Mock
  private UserDetails userDetails;
  
  @InjectMocks
  private HomeController homeController;
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  public void testGetHome_WithAuthenticatedUser() {
    User user = new User();
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setCompanyName(COMPANY_NAME);
    user.setCompanyDetails(companyDetails);
    
    when(userDetails.getUsername()).thenReturn(TEST_EMAIL);
    when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(user);
    
    String viewName = homeController.getHome(model, userDetails);
    
    verify(model).addAttribute(ATTRIBUTE_COMPANY_NAME, COMPANY_NAME);
    assertEquals(VIEW_INDEX, viewName);
  }
  
  @Test
  public void testGetHome_WithoutAuthenticatedUser() {
    String viewName = homeController.getHome(model, null);
    
    verify(model, never()).addAttribute(anyString(), any());
    assertEquals(VIEW_INDEX, viewName);
  }
}