package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.model.dto.bankAccount.BankAccountView;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceEditDto;
import bg.softuni.invoice_app.model.dto.invoice.InvoiceView;
import bg.softuni.invoice_app.model.dto.recipientDetails.RecipientDetailsView;
import bg.softuni.invoice_app.model.entity.User;
import bg.softuni.invoice_app.service.bankAccount.BankAccountService;
import bg.softuni.invoice_app.service.invoice.InvoiceService;
import bg.softuni.invoice_app.service.pdf.PdfGenerationService;
import bg.softuni.invoice_app.service.recipientDetails.RecipientDetailsService;
import bg.softuni.invoice_app.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

import static bg.softuni.invoice_app.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InvoicesControllerTest {
  
  @Mock
  private InvoiceService invoiceService;
  
  @Mock
  private PdfGenerationService pdfService;
  
  @Mock
  private RecipientDetailsService recipientDetailsService;
  
  @Mock
  private BankAccountService bankAccountService;
  
  @Mock
  private UserService userService;
  
  @Mock
  private Model model;
  
  @Mock
  private BindingResult bindingResult;
  
  @Mock
  private RedirectAttributes redirectAttributes;
  
  @InjectMocks
  private InvoicesController invoicesController;
  
  private User mockUser;
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    
    mockUser = new User();
    mockUser.setUuid(UUID.randomUUID().toString());
  }
  
  @Test
  public void testCreateInvoiceWithClient() {
    RecipientDetailsView recipientDetailsView = new RecipientDetailsView();
    List<BankAccountView> bankAccounts = List.of(new BankAccountView(), new BankAccountView());
    
    when(recipientDetailsService.findById(TEST_ID)).thenReturn(recipientDetailsView);
    when(userService.getUser()).thenReturn(mockUser);
    when(bankAccountService.getUserAccounts(mockUser.getUuid())).thenReturn(bankAccounts);
    
    String viewName = invoicesController.createInvoiceWithClient(TEST_ID, model);
    
    assertEquals("invoice-create-with-client", viewName);
    verify(model).addAttribute("recipient", recipientDetailsView);
    verify(model).addAttribute("bankAccounts", bankAccounts);
  }
  
  
  @Test
  public void testViewInvoice() {
    InvoiceView invoiceView = new InvoiceView();
    when(invoiceService.getById(TEST_ID)).thenReturn(invoiceView);
    
    String viewName = invoicesController.viewInvoice(TEST_ID, model);
    assertEquals(VIEW_INVOICE_VIEW, viewName);
    verify(model).addAttribute(ATTRIBUTE_INVOICE, invoiceView);
  }
  
  
  @Test
  public void testDeleteInvoice() {
    String viewName = invoicesController.deleteInvoice(TEST_ID);
    assertEquals("redirect:/invoices", viewName);
    verify(invoiceService).deleteById(TEST_ID);
  }
  @Test
  public void testUpdateInvoice_WithoutBindingErrors() {
    when(bindingResult.hasErrors()).thenReturn(false);
    when(invoiceService.isInvoiceNumberUniqueOrSame(eq(TEST_ID), any(Long.class))).thenReturn(true);
    
    InvoiceEditDto invoiceData = new InvoiceEditDto();
    String viewName = invoicesController.updateInvoice(TEST_ID, invoiceData, bindingResult, model);
    
    assertEquals("redirect:/invoices", viewName);
    verify(invoiceService).updateInvoice(eq(TEST_ID), any(InvoiceEditDto.class));
  }
  
}
