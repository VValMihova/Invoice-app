package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.entity.ArchiveInvoice;
import bg.softuni.invoice_app.model.entity.CompanyDetails;
import bg.softuni.invoice_app.model.entity.User;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;

import java.util.List;

import static bg.softuni.invoice_app.TestConstants.TEST_ID;
import static bg.softuni.invoice_app.TestConstants.TEST_ID_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminControllerTest {
  
  @Mock
  private ArchiveInvoiceService archiveInvoiceService;
  
  @Mock
  private UserService userService;
  
  @Mock
  private Model model;
  
  @InjectMocks
  private AdminController adminController;
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void testMakeAdmin() {
    Long userId = TEST_ID;
    
    String viewName = adminController.makeAdmin(userId);
    
    assertEquals("redirect:/admin", viewName);
    verify(userService).addAdminRoleToUser(userId);
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void testRemoveAdmin() {
    Long userId = TEST_ID;
    
    String viewName = adminController.removeAdmin(userId);
    
    assertEquals("redirect:/admin", viewName);
    verify(userService, times(1)).removeAdminRoleFromUser(userId);
  }
  
  @Test
  public void testViewDeletedInvoices() {
    Long userId = TEST_ID;
    int page = 0;
    ArchiveInvoice archiveInvoice = new ArchiveInvoice();
    Page<ArchiveInvoice> deletedInvoices = new PageImpl<>(List.of(archiveInvoice));
    CompanyDetails companyDetails = new CompanyDetails();
    companyDetails.setCompanyName("Test Company");
    User user = new User();
    user.setCompanyDetails(companyDetails);
    
    when(archiveInvoiceService.findAllByUserId(eq(userId), any(PageRequest.class)))
        .thenReturn(deletedInvoices);
    when(userService.findById(userId)).thenReturn(user);
    
    String result = adminController.viewDeletedInvoices(model, userId, page);
    
    
    assertEquals("admin-deleted-invoices", result);
    verify(model).addAttribute("deletedInvoices", deletedInvoices);
    verify(model).addAttribute("companyName", "Test Company");
    verify(model).addAttribute("userId", userId);
  }
  
  @Test
  public void testRestoreInvoice() {
    Long testId = TEST_ID;
    Long userId = TEST_ID_2;
    
    String result = adminController.restoreInvoice(testId, userId);
    
    assertEquals("redirect:/admin/deleted-invoices?userId=" + userId, result);
    verify(archiveInvoiceService).restoreInvoice(testId);
  }
}
