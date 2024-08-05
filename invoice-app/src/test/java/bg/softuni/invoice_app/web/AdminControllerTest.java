package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.TestConstants;
import bg.softuni.invoice_app.model.entity.ArchiveInvoice;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.model.user.InvoiceAppUserDetails;
import bg.softuni.invoice_app.service.archive.ArchiveInvoiceService;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminControllerTest {
  
  @Mock
  private UserService userService;
  
  @Mock
  private ArchiveInvoiceService archiveInvoiceService;
  
  @Mock
  private Model model;
  
  @InjectMocks
  private AdminController adminController;
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  public void testViewDeletedInvoices() {
    ArchiveInvoice archiveInvoice = new ArchiveInvoice();
    Page<ArchiveInvoice> deletedInvoices = new PageImpl<>(List.of(archiveInvoice));
    User user = new User();
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setCompanyName(COMPANY_NAME);
    user.setCompanyDetails(companyDetails);
    
    when(archiveInvoiceService.findAllByUserId(eq(TEST_ID), any(PageRequest.class)))
        .thenReturn(deletedInvoices);
    when(userService.findById(TEST_ID)).thenReturn(user);
    
    String viewName = adminController.viewDeletedInvoices(model, TEST_ID, 0);
    
    assertEquals(TestConstants.VIEW_ADMIN_DELETED_INVOICES, viewName);
    verify(model).addAttribute(TestConstants.ATTRIBUTE_DELETED_INVOICES, deletedInvoices);
    verify(model).addAttribute(TestConstants.ATTRIBUTE_COMPANY_NAME, COMPANY_NAME);
    verify(model).addAttribute(TestConstants.ATTRIBUTE_USER_ID, TEST_ID);
  }
  
  @Test
  public void testRestoreInvoice() {
    doNothing().when(archiveInvoiceService).restoreInvoice(TEST_ID);
    
    String viewName = adminController.restoreInvoice(TEST_ID, TEST_ID);
    
    assertEquals(TestConstants.REDIRECT_ADMIN_DELETED_INVOICES + TEST_ID, viewName);
    verify(archiveInvoiceService).restoreInvoice(TEST_ID);
  }
}
