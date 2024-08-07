package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsAddDto;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsEdit;
import bg.softuni.invoice_app.model.entity.RecipientDetails;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipientDetailsControllerIT {
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private RecipientDetailsService recipientDetailsService;
  
  @MockBean
  private UserService userService;
  
  
  @Test
  public void shouldShowClientsPage() throws Exception {
    when(recipientDetailsService.findAll()).thenReturn(Collections.emptyList());
    
    mockMvc.perform(get(CLIENTS_URL).with(user("user").password("password").roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name(CLIENTS_PAGE_VIEW))
        .andExpect(model().attributeExists("clients"));
  }
  
  @Test
  public void shouldShowAddClientForm() throws Exception {
    mockMvc.perform(get(ADD_CLIENT_URL).with(user("user").password("password").roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name(ADD_CLIENT_FORM_VIEW));
  }
  
  @Test
  public void shouldAddClient() throws Exception {
    CompanyDetailsView companyDetailsView = new CompanyDetailsView();
    companyDetailsView.setCompanyName(COMPANY_NAME);
    companyDetailsView.setAddress(COMPANY_ADDRESS);
    companyDetailsView.setManager(COMPANY_MANAGER);
    companyDetailsView.setEik(COMPANY_EIK);
    companyDetailsView.setVatNumber(COMPANY_VAT_NUMBER);
    when(userService.showCompanyDetails()).thenReturn(companyDetailsView);
    
    RecipientDetailsAddDto recipientDetails = new RecipientDetailsAddDto();
    recipientDetails.setCompanyName(RECIPIENT_NAME);
    recipientDetails.setAddress(RECIPIENT_ADDRESS);
    recipientDetails.setEik(RECIPIENT_EIK);
    recipientDetails.setVatNumber(RECIPIENT_VAT_NUMBER);
    recipientDetails.setManager(RECIPIENT_MANAGER);
    
    doNothing().when(recipientDetailsService).addRecipientDetails(any(RecipientDetailsAddDto.class));
    
    mockMvc.perform(post(ADD_URL)
            .param("companyName", recipientDetails.getCompanyName())
            .param("address", recipientDetails.getAddress())
            .param("vatNumber", recipientDetails.getVatNumber())
            .param("manager", recipientDetails.getManager())
            .param("eik", recipientDetails.getEik())
            .with(csrf())
            .with(user(TEST_EMAIL).password(TEST_PASSWORD)))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/clients"));
    
    verify(recipientDetailsService, times(1)).addRecipientDetails(any(RecipientDetailsAddDto.class));
  }
  
  @Test
  public void shouldShowEditClientForm() throws Exception {
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setId(TEST_ID);
    recipientDetails.setCompanyName(COMPANY_NAME);
    
    when(recipientDetailsService.getById(TEST_ID)).thenReturn(recipientDetails);
    
    mockMvc.perform(get(EDIT_CLIENT_URL_TEMPLATE, TEST_ID).with(user("user").password("password").roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name(EDIT_CLIENT_FORM_VIEW))
        .andExpect(model().attributeExists("recipientDetailsEdit"));
  }
  
  @Test
  public void shouldEditClient() throws Exception {
    CompanyDetailsView companyDetailsView = new CompanyDetailsView();
    companyDetailsView.setCompanyName(COMPANY_NAME);
    companyDetailsView.setAddress(COMPANY_ADDRESS);
    companyDetailsView.setManager(COMPANY_MANAGER);
    companyDetailsView.setEik(COMPANY_EIK);
    companyDetailsView.setVatNumber(COMPANY_VAT_NUMBER);
    when(userService.showCompanyDetails()).thenReturn(companyDetailsView);
    
    RecipientDetails recipientDetails = new RecipientDetails();
    recipientDetails.setId(TEST_ID);
    recipientDetails.setCompanyName(RECIPIENT_NAME);
    recipientDetails.setAddress(RECIPIENT_ADDRESS);
    recipientDetails.setEik(RECIPIENT_EIK);
    recipientDetails.setVatNumber(RECIPIENT_VAT_NUMBER);
    recipientDetails.setManager(RECIPIENT_MANAGER);
    when(recipientDetailsService.getById(TEST_ID)).thenReturn(recipientDetails);
    
    RecipientDetailsEdit recipientDetailsEdit = new RecipientDetailsEdit();
    recipientDetailsEdit.setCompanyName(RECIPIENT_NAME);
    recipientDetailsEdit.setAddress(RECIPIENT_ADDRESS);
    recipientDetailsEdit.setEik(RECIPIENT_EIK);
    recipientDetailsEdit.setVatNumber(RECIPIENT_VAT_NUMBER);
    recipientDetailsEdit.setManager(RECIPIENT_MANAGER);
    
    mockMvc.perform(post(EDIT_URL)
            .flashAttr("recipientDetailsEdit", recipientDetailsEdit)
            .with(csrf())
            .with(user(TEST_EMAIL).password(TEST_PASSWORD)))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/clients"));
  }
}