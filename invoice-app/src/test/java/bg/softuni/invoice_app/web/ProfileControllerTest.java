package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountCreateBindingDto;
import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsEditBindingDto;
import bg.softuni.invoice_app.model.dto.companyDetails.CompanyDetailsView;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.companyDetails.CompanyDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {
  
  @Mock
  private BankAccountService bankAccountService;
  
  @Mock
  private Model model;
  
  @Mock
  private BindingResult bindingResult;
  
  @Mock
  private RedirectAttributes redirectAttributes;
  
  @InjectMocks
  private ProfileController profileController;
  
  private CompanyDetailsView mockCompanyDetailsView;
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockCompanyDetailsView = new CompanyDetailsView();
  }
  
  
  @Test
  public void testShowAddBankAccountForm() {
    String viewName = profileController.showAddBankAccountForm();
    assertEquals(VIEW_BANK_ACCOUNT_ADD, viewName);
  }
  
  @Test
  public void testAddBankAccount_WithBindingErrors() {
    when(bindingResult.hasErrors()).thenReturn(true);
    
    String viewName = profileController.addBankAccount(new BankAccountCreateBindingDto(), bindingResult, redirectAttributes);
    
    assertEquals(REDIRECT_PROFILE_ADD_BANK_ACCOUNT, viewName);
    verify(redirectAttributes).addFlashAttribute(eq(ATTRIBUTE_BANK_ACCOUNT_DATA), any());
    verify(redirectAttributes).addFlashAttribute(eq(ATTRIBUTE_BINDING_RESULT + ATTRIBUTE_BANK_ACCOUNT_DATA), eq(bindingResult));
  }
  
  
  @Test
  public void testEditBankAccount_WithBindingErrors() {
    when(bindingResult.hasErrors()).thenReturn(true);
    
    String viewName = profileController.editBankAccount(TEST_ID, new BankAccountEditBindingDto(), bindingResult, model);
    
    assertEquals(VIEW_BANK_ACCOUNT_EDIT, viewName);
    verify(model).addAttribute(eq(ATTRIBUTE_BANK_ACCOUNT_DATA_EDIT), any());
    verify(model).addAttribute(eq(ATTRIBUTE_BINDING_RESULT + ATTRIBUTE_BANK_ACCOUNT_DATA_EDIT), eq(bindingResult));
  }
  
  @Test
  public void testEditBankAccount_WithoutBindingErrors() {
    when(bindingResult.hasErrors()).thenReturn(false);
    
    String viewName = profileController.editBankAccount(TEST_ID, new BankAccountEditBindingDto(), bindingResult, model);
    
    assertEquals(REDIRECT_PROFILE, viewName);
    verify(bankAccountService).updateBankAccount(eq(TEST_ID), any(BankAccountEditBindingDto.class));
  }
  
  @Test
  public void testDeleteBankAccount() {
    String viewName = profileController.deleteBankAccount(TEST_ID);
    
    assertEquals(REDIRECT_PROFILE, viewName);
    verify(bankAccountService).deleteBankAccount(eq(TEST_ID));
  }
}
