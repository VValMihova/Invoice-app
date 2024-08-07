package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static bg.softuni.invoice_app.TestConstants.COMPANY_EIK;
import static bg.softuni.invoice_app.TestConstants.COMPANY_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {
  
  @Autowired
  private MockMvc mockMvc;
  
  @Mock
  private UserService userService;
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void testAdminPanelWithCompanyNameAndEik() throws Exception {
    User user1 = new User();
    User user2 = new User();
    Page<User> users = new PageImpl<>(List.of(user1, user2));
    when(userService.findAllExceptCurrent(any(PageRequest.class), eq(COMPANY_NAME), eq(COMPANY_EIK))).thenReturn(users);
    when(userService.isUserAdmin(any(User.class))).thenReturn(true);
    
    mockMvc.perform(get("/admin")
            .param("page", "0")
            .param("companyName", COMPANY_NAME)
            .param("eik", COMPANY_EIK))
        .andExpect(status().isOk())
        .andExpect(view().name("admin-panel"))
        .andExpect(model().attributeExists("users"))
        .andExpect(model().attributeExists("isAdminList"))
        .andExpect(model().attribute("companyName", COMPANY_NAME))
        .andExpect(model().attribute("eik", COMPANY_EIK));
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void testAdminPanelAttributesAndView() throws Exception {
    User user = new User();
    Page<User> users = new PageImpl<>(List.of(user));
    when(userService.findAllExceptCurrent(any(PageRequest.class), any(), any())).thenReturn(users);
    when(userService.isUserAdmin(any(User.class))).thenReturn(true);
    
    mockMvc.perform(get("/admin")
            .param("page", "0")
            .param("companyName", "")
            .param("eik", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("admin-panel"))
        .andExpect(model().attributeExists("users"))
        .andExpect(model().attributeExists("isAdminList"))
        .andExpect(model().attribute("companyName", ""))
        .andExpect(model().attribute("eik", ""));
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void testFindAllExceptCurrentCall() throws Exception {
    User user = new User();
    Page<User> users = new PageImpl<>(List.of(user));
    when(userService.findAllExceptCurrent(any(PageRequest.class), any(), any())).thenReturn(users);
    when(userService.isUserAdmin(any(User.class))).thenReturn(true);
    
    mockMvc.perform(get("/admin")
            .param("page", "0")
            .param("companyName", "")
            .param("eik", ""))
        .andExpect(status().isOk());
  }
}
