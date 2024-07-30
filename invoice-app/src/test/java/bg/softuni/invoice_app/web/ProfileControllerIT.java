package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

import static bg.softuni.invoice_app.util.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProfileControllerIT {
  
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private UserService userService;
  
  @MockBean
  private BankAccountService bankAccountService;
  
  
  @Test
  @WithMockUser(username = TEST_EMAIL, password = TEST_PASSWORD)
  public void shouldReturnUserProfileView() throws Exception {
    CompanyDetailsView companyDetailsView = new CompanyDetailsView();
    companyDetailsView.setCompanyName(COMPANY_NAME);
    companyDetailsView.setAddress(COMPANY_ADDRESS);
    companyDetailsView.setManager(COMPANY_MANAGER);
    companyDetailsView.setEik(COMPANY_EIK);
    companyDetailsView.setVatNumber(COMPANY_VAT_NUMBER);
    Mockito.when(userService.showCompanyDetails()).thenReturn(companyDetailsView);
    
    User mockUser = Mockito.mock(User.class);
    UUID userUuid = UUID.randomUUID();
    Mockito.when(mockUser.getUuid()).thenReturn(String.valueOf(userUuid));
    Mockito.when(userService.getUser()).thenReturn(mockUser);
    
    List<BankAccountView> bankAccounts = List.of(
        new BankAccountView(BIC_CODE, CURRENCY_1, IBAN_1),
        new BankAccountView(BIC_CODE, CURRENCY_2, IBAN_2)
    );
    Mockito.when(bankAccountService.getUserAccounts(String.valueOf(userUuid))).thenReturn(bankAccounts);
    
    MvcResult result = mockMvc.perform(get(PROFILE_URL))
        .andExpect(status().isOk())
        .andReturn();
    
    ModelAndView modelAndView = result.getModelAndView();
    assertThat(modelAndView).isNotNull();
    assertThat(modelAndView.getViewName()).isEqualTo(USER_PROFILE_VIEW);
    assertThat(modelAndView.getModel().get("companyDetails")).isEqualTo(companyDetailsView);
    assertThat(modelAndView.getModel().get("bankAccounts")).isEqualTo(bankAccounts);
  }
  
  @Test
  @WithMockUser(username = TEST_EMAIL, password = TEST_PASSWORD)
  public void shouldReturnEditCompanyView() throws Exception {
    CompanyDetailsView companyDetailsView = new CompanyDetailsView();
    companyDetailsView.setCompanyName(COMPANY_NAME);
    companyDetailsView.setAddress(COMPANY_ADDRESS);
    companyDetailsView.setManager(COMPANY_MANAGER);
    companyDetailsView.setEik(COMPANY_EIK);
    companyDetailsView.setVatNumber(COMPANY_VAT_NUMBER);
    Mockito.when(userService.showCompanyDetails()).thenReturn(companyDetailsView);
    
    MvcResult result = mockMvc.perform(get(EDIT_COMPANY_URL))
        .andExpect(status().isOk())
        .andReturn();
    
    ModelAndView modelAndView = result.getModelAndView();
    assertThat(modelAndView).isNotNull();
    assertThat(modelAndView.getViewName()).isEqualTo(COMPANY_EDIT_VIEW);
    assertThat(modelAndView.getModel().get("companyDetails")).isEqualTo(companyDetailsView);
  }
  
}