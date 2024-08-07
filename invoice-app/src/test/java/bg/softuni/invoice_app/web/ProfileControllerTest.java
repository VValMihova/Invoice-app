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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {
  
  @Mock
  private BankAccountService bankAccountService;
  
  @Mock
  private CompanyDetailsService companyDetailsService;
  
  @Mock
  private UserService userService;
  
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
  
  @Test
  public void testUpdateCompany_WithBindingErrors() {
    when(bindingResult.hasErrors()).thenReturn(true);
    
    String viewName = profileController.updateCompany(new CompanyDetailsEditBindingDto(), bindingResult, model);
    
    assertEquals("company-edit", viewName);
    verify(model).addAttribute(eq("companyDetails"), any(CompanyDetailsEditBindingDto.class));
    verify(model).addAttribute(eq("org.springframework.validation.BindingResult.companyDetails"), eq(bindingResult));
  }
  
  @Test
  public void testUpdateCompany_WithoutBindingErrors() {
    when(bindingResult.hasErrors()).thenReturn(false);
    CompanyDetails mockCompanyDetails = new CompanyDetails();
    when(companyDetailsService.update(anyLong(), any(CompanyDetailsEditBindingDto.class))).thenReturn(mockCompanyDetails);
    
    CompanyDetailsView companyDetailsView = new CompanyDetailsView();
    companyDetailsView.setId(1L);
    when(userService.showCompanyDetails()).thenReturn(companyDetailsView);
    
    CompanyDetailsEditBindingDto companyDetailsEditBindingDto = new CompanyDetailsEditBindingDto();
    companyDetailsEditBindingDto.setCompanyName("Valid Company Name");
    companyDetailsEditBindingDto.setAddress("Valid Address");
    companyDetailsEditBindingDto.setManager("Valid Manager");
    companyDetailsEditBindingDto.setEik("Valid EIK");
    companyDetailsEditBindingDto.setVatNumber("Valid VAT");
    
    String viewName = profileController.updateCompany(companyDetailsEditBindingDto, bindingResult, model);
    
    assertEquals("redirect:/profile", viewName);
    verify(companyDetailsService).update(eq(1L), any(CompanyDetailsEditBindingDto.class));
    verify(userService).updateCompany(mockCompanyDetails);
  }
}
