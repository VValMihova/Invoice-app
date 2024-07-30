package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.exeption.DatabaseException;
import bg.softuni.invoice_app.model.dto.user.UserRegisterBindingDto;
import bg.softuni.invoice_app.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static bg.softuni.invoice_app.util.TestConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {
  
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private UserService userService;
  
  @Test
  public void shouldShowLoginPage() throws Exception {
    mockMvc.perform(get(LOGIN_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(LOGIN_VIEW_NAME));
  }
  
  @Test
  public void shouldShowLoginPageWithError() throws Exception {
    mockMvc.perform(get("/users/login").param(LOGIN_ERROR_PARAM, "true"))
        .andExpect(status().isOk())
        .andExpect(view().name(LOGIN_VIEW_NAME))
        .andExpect(model().attributeExists("loginError"));
  }
  
  @Test
  public void shouldShowRegisterPage() throws Exception {
    mockMvc.perform(get(REGISTER_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(REGISTER_VIEW_NAME));
  }
  
  @Test
  public void shouldRegisterUser() throws Exception {
    UserRegisterBindingDto user = new UserRegisterBindingDto();
    
    user.setEmail(TEST_EMAIL);
    user.setPassword(TEST_PASSWORD);
    
    mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(user))
            .with(csrf()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/users/login"));
  }
  
  @Test
  public void shouldHandleDatabaseException() throws Exception {
    UserRegisterBindingDto user = new UserRegisterBindingDto();
    user.setEmail(TEST_EMAIL);
    user.setPassword(TEST_PASSWORD);
    user.setCompanyName(COMPANY_NAME);
    user.setConfirmPassword(TEST_CONFIRM_PASSWORD);
    user.setAddress(COMPANY_ADDRESS);
    user.setVatNumber(COMPANY_VAT_NUMBER);
    user.setEik(COMPANY_EIK);
    user.setManager(COMPANY_MANAGER);
    
    Mockito.doThrow(new DatabaseException("Database error")).when(userService).register(Mockito.any());
    
    mockMvc.perform(post(REGISTER_URL)
            .flashAttr("registerData", user)
            .with(csrf()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(REDIRECT_REGISTER_URL));
  }
  
  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}